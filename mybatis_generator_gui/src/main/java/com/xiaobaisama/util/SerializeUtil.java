package com.xiaobaisama.util;

import java.io.*;

/**
 * Created by xiaobai on 2018/6/27.
 */
public class SerializeUtil {

    public static void serialize(File saveFile,Serializable ser) throws IOException {
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(saveFile));
            oos.writeObject(ser);
            oos.flush();
        }finally{
            if(oos!=null){
                oos.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T desSerialize(File loadFile) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        T result;
        try{
            ois = new ObjectInputStream(new FileInputStream(loadFile));
            result = (T)ois.readObject();
        }finally{
            if(ois != null){
                ois.close();
            }
        }
        return result;
    }


}
