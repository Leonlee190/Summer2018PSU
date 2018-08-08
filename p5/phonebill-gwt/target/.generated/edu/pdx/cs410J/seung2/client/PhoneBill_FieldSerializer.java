package edu.pdx.cs410J.seung2.client;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class PhoneBill_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.util.Collection getCalls(edu.pdx.cs410J.seung2.client.PhoneBill instance) /*-{
    return instance.@edu.pdx.cs410J.seung2.client.PhoneBill::calls;
  }-*/;
  
  private static native void setCalls(edu.pdx.cs410J.seung2.client.PhoneBill instance, java.util.Collection value) 
  /*-{
    instance.@edu.pdx.cs410J.seung2.client.PhoneBill::calls = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, edu.pdx.cs410J.seung2.client.PhoneBill instance) throws SerializationException {
    setCalls(instance, (java.util.Collection) streamReader.readObject());
    
    edu.pdx.cs410J.AbstractPhoneBill_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static edu.pdx.cs410J.seung2.client.PhoneBill instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new edu.pdx.cs410J.seung2.client.PhoneBill();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, edu.pdx.cs410J.seung2.client.PhoneBill instance) throws SerializationException {
    streamWriter.writeObject(getCalls(instance));
    
    edu.pdx.cs410J.AbstractPhoneBill_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return edu.pdx.cs410J.seung2.client.PhoneBill_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    edu.pdx.cs410J.seung2.client.PhoneBill_FieldSerializer.deserialize(reader, (edu.pdx.cs410J.seung2.client.PhoneBill)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    edu.pdx.cs410J.seung2.client.PhoneBill_FieldSerializer.serialize(writer, (edu.pdx.cs410J.seung2.client.PhoneBill)object);
  }
  
}
