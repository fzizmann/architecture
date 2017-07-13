package de.thb.fz.dependency;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.objectweb.asm.ClassReader;

public class DependencyInitialisator {
    private String basePath;

    private ArrayList<String> generateClassList(File dir) {
        ArrayList pathList = new ArrayList();
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory()) {
                    pathList.addAll(this.generateClassList(files[i]));
                } else {
                    int strLen = files[i].getName().length();
                    if (files[i].getName().substring(strLen - 5, strLen).equals("class")) {
                        pathList.add(files[i].getAbsolutePath());
                    }
                }
            }
        }

        return pathList;
    }

    public ArrayList<Package> findDependancys(Package pack) {
        ArrayList packageList = new ArrayList();
        DependencyVisitor v = new DependencyVisitor();

        try {
            String e = this.basePath + "//" + pack.getName();
            Iterator var6 = this.generateClassList(new File(e)).iterator();

            while (var6.hasNext()) {
                String path = (String) var6.next();
                FileInputStream f = new FileInputStream(path);
                (new ClassReader(f)).accept(v, 0);
                Set classPackages = v.getPackages();
                Iterator var10 = classPackages.iterator();

                while (var10.hasNext()) {
                    String className = (String) var10.next();
                    Package newPackage = className.getClass().getPackage();
//                    newPackage.setClassName(strclass[strclass.length - 1]);
//                    newPackage.setFile(path);
                    packageList.add(newPackage);
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        }

        return packageList;
    }

    public DependencyInitialisator(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
