package cityguide.com.cityguide;

import android.content.Context;
import android.support.v7.widget.CardView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Appointment implements Serializable {
    private static final String FILE_NAME = "appointment.data";
    private Float originLat;
    private Float originLong;
    private Float destinyLat;
    private Float destinyLong;
    private Date date;

    public Float getOriginLat(){
        return originLat;
    };

    public Float getOriginLong(){
        return originLong;
    };

    public Float getDetinyLat(){
        return destinyLat;
    }

    public Float getDestinyLong(){
        return destinyLong;
    }

    public Date getDate(){
        return date;
    }

    public Float setOriginLat(Float originLat){
        return this.originLat = originLat;
    };

    public Float setOriginLong(Float originLong){
        return this.originLong = originLong;
    };

    public Float setDestinyLat(Float destinyLat){
        return this.destinyLat = destinyLat;
    };

    public Float setDestinyLong(Float destinyLong){
        return this.destinyLong = destinyLong;
    };

    public Date setDate(Date date){
        return this.date = date;

    };

    public static boolean saveObject(ArrayList<Appointment> obj, Context context){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean keep = true;

        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();

            fos.close();
            oos.close();
        } catch (Exception e) {
            keep = false;
        }
        return keep;
    };

    /*public static CardView<Appointment> getObject(Context context){
        CardView<Appointment> appointments = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = context.openFileInput(FILE_NAME);
            ois = new ObjectInputStream(fis);
            appointments = (CardView<Appointment>) ois.readObject();

        }catch (Exception e){
            appointments = null;
        }

        return appointments;
    }*/

}
