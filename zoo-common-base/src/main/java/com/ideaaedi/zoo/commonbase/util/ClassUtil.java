package com.ideaaedi.zoo.commonbase.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Java获取指定包名下的所有类的全类名的解决方案
 *
 * @author JustryDeng
 * @since 2020/5/1 20:21:22
 */
@Slf4j
public final class ClassUtil {

    private static final String CLASS_SUFFIX = ".class";
    private static final String CLASS_FILE_PREFIX = File.separator + "classes" + File.separator;
    private static final int CLASS_FILE_PREFIX_LENGTH = CLASS_FILE_PREFIX.length();
    private static final String PACKAGE_SEPARATOR = ".";

    /**
     * 查找指定包下的所有类的全类名
     *
     * @param packageName 包名 (形如: com.alibaba.fastjson.util)
     * @param shouldRecursive 是否递归查找子包
     *
     * @return 全类名 集
     */
    public static List<String> getClazzName(String packageName, boolean shouldRecursive) {
        log.info("getClazzName param packageName -> {}, shouldRecursive -> {}", packageName, shouldRecursive);
        if (StringUtils.isBlank(packageName)) {
            throw new RuntimeException(" packageName cannot be empty");
        }
        List<String> result = new ArrayList<>(16);
        String suffixPath = packageName.replaceAll("\\.", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = loader.getResources(suffixPath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url == null) {
                    continue;
                }
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String path = url.getPath();
                    result.addAll(getAllClassNameByFile(new File(path), shouldRecursive));
                } else if ("jar".equals(protocol)) {
                    JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                    if (jarFile != null) {
                        result.addAll(getAllClassNameByJar(jarFile, packageName, shouldRecursive));
                    }
                } else {
                    throw new UnsupportedOperationException(" cannot support handle protocol -> [" + protocol + "]");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug("getClazzName result -> {}", result);
        return result;
    }


    /**
     * 获取文件中, class文件的全类名
     *
     * @param file 文件
     * @param shouldRecursive 是否递归查找子包
     * @return 全类名 集
     */
    private static List<String> getAllClassNameByFile(File file, boolean shouldRecursive) {
        List<String> result = new ArrayList<>(16);
        if (!file.exists()) {
            log.info(" file[{}] non exist", file.getAbsolutePath());
            return result;
        }
        // 如果是文件
        if (file.isFile()) {
            String path = file.getPath();
            // 这里替换文件分割符要用replace。因为replaceAll里面的参数是正则表达式,而windows环境中File.separator="\\"的,因此会有问题
            if (path.endsWith(CLASS_SUFFIX)) {
                path = path.replace(CLASS_SUFFIX, "");
                // 从"/classes/"后面开始截取
                String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX_LENGTH)
                        .replace(File.separator, PACKAGE_SEPARATOR);
                // 如果是内部类的话，不作添加
                if (!clazzName.contains("$")) {
                    result.add(clazzName);
                }
            }
        // 如果是目录
        } else {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                return result;
            }
            for (File fileItem : listFiles) {
                if (shouldRecursive) {
                    result.addAll(getAllClassNameByFile(fileItem, shouldRecursive));
                } else {
                    if (!fileItem.isFile()) {
                        continue;
                    }
                    String path = fileItem.getPath();
                    if (path.endsWith(CLASS_SUFFIX)) {
                        path = path.replace(CLASS_SUFFIX, "");
                        String clazzName = getClassNameByPath(path);
                        if (!clazzName.contains("$")) {
                            result.add(clazzName);
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * 获取jar文件中, class文件的全类名
     *
     * @param jarFile jar文件
     * @param packageName 包名
     * @param shouldRecursive 是否递归查找子包
     * @return List
     */
    private static List<String> getAllClassNameByJar(JarFile jarFile, String packageName, boolean shouldRecursive) {
        List<String> result = new ArrayList<>(16);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            // 判断是不是class文件
            if (!name.endsWith(CLASS_SUFFIX)) {
                continue;
            }
            log.debug(" JarEntry name before processing is -> {}", name);
            name = name.replace(CLASS_SUFFIX, "").replace("/", ".");
            log.debug(" JarEntry name after processing is -> {}", name);
            // 是否是内部类
            boolean isInnerClass = name.contains("$");
            if (shouldRecursive) {
                // 如果要子包的文件,那么就只要开头相同且不是内部类就ok
                if (name.startsWith(packageName) && !isInnerClass) {
                    result.add(name);
                }
            } else {
                // 如果不要子包的文件,那么就必须保证最后一个"."之前的字符串和包名一样且不是内部类
                if (packageName.equals(name.substring(0, name.lastIndexOf("."))) && !isInnerClass) {
                    result.add(name);
                }
            }
        }
        return result;
    }

    /**
     * 根据文件全路径获取该文件对应的类的全类名
     *
     * @param path 文件全路径
     * @return 该文件对应的类的全类名
     */
    private static String getClassNameByPath(String path) {
        log.debug(" getClassNameByPath path -> {}", path);
        path = path.replace(CLASS_SUFFIX, "");
        // 从"/classes/"后面开始截取
        String result = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length())
                            .replace(File.separator, PACKAGE_SEPARATOR);
        log.debug(" getClassNameByPath result -> {}", result);
        return result;

    }

    /** test */
    public static void main(String[] args) {
        List<String> list = ClassUtil.getClazzName("com.alibaba.fastjson.asm", false);
        list.forEach(System.out::println);
        System.err.println();
    }

}