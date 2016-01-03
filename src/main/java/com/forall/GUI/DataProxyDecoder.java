/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.GUI;

import com.forall.modell.DataProxy;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author jd
 */
public class DataProxyDecoder implements Decoder.Binary<DataProxy> {

    @Override
    public DataProxy decode(ByteBuffer bytes) throws DecodeException {

        ByteArrayInputStream bi = new ByteArrayInputStream(bytes.array());
        DataProxy obj = null;
        try {
            obj = DataProxy.class.cast(new ObjectInputStream(bi).readObject());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DataProxyDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    @Override
    public boolean willDecode(ByteBuffer bytes) {
        return bytes != null;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}