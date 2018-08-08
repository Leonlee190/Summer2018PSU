package edu.pdx.cs410J.seung2.client;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class PhoneCall_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, edu.pdx.cs410J.seung2.client.PhoneCall instance) throws SerializationException {
    
    edu.pdx.cs410J.AbstractPhoneCall_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static edu.pdx.cs410J.seung2.client.PhoneCall instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new edu.pdx.cs410J.seung2.client.PhoneCall();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, edu.pdx.cs410J.seung2.client.PhoneCall instance) throws SerializationException {
    
    edu.pdx.cs410J.AbstractPhoneCall_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return edu.pdx.cs410J.seung2.client.PhoneCall_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    edu.pdx.cs410J.seung2.client.PhoneCall_FieldSerializer.deserialize(reader, (edu.pdx.cs410J.seung2.client.PhoneCall)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    edu.pdx.cs410J.seung2.client.PhoneCall_FieldSerializer.serialize(writer, (edu.pdx.cs410J.seung2.client.PhoneCall)object);
  }
  
}
