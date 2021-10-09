package com.company;

import javax.crypto.spec.PSource;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Servidor
{
    private ServerSocket servidor;
    private Socket socket;
    private int puerto = 5000;
    private DataOutputStream salida;
    private DataInputStream flujo;
    private InputStream entrada;
    private StringTokenizer stringTokenizer;
    private ArrayList<String> inscritos = new ArrayList<>();

    public void iniciar()
    {
        try
        {
            this.servidor = new ServerSocket(this.puerto);
            this.socket = new Socket();
            boolean continuar = true;
            while ( continuar)
            {
                this.socket = this.servidor.accept();
                this.entrada = this.socket.getInputStream();
                this.flujo = new DataInputStream(this.entrada);
                String mensaje =this.flujo.readUTF();
                System.out.println("el mensaje es: "+ mensaje);
                this.stringTokenizer = new StringTokenizer(mensaje);
                String respuesta;
                String operacion = stringTokenizer.nextToken();
                if(operacion.equals("1"))
                {
                    String nombre = this.compactarString();
                    if (!this.inscritos.contains(nombre))
                    {
                        this.inscritos.add(nombre);
                        respuesta = nombre + "fue agregado correctamente.";
                    }
                    else
                    {
                        respuesta = nombre + "ya se encuenta en la lista";
                    }

                }
                else if(operacion.equals("2"))
                {
                    respuesta = this.formatearLista();

                }
                else if(operacion.equals("3"))
                {
                    respuesta = "apagando servicio";
                    continuar = false;
                }
                else
                {
                    respuesta = "operacion erronea";
                }
                this.salida = new DataOutputStream(this.socket.getOutputStream());
                this.salida.writeUTF(respuesta);
            }
            System.out.println("mucho trabajo por hoy adios");
            this.socket.close();
        }
        catch (Exception e)
        {

        }
    }

    private String compactarString()
    {
        StringBuilder st = new StringBuilder();
        while (this.stringTokenizer.hasMoreTokens())
        {
            st.append(this.stringTokenizer.nextToken());
            st.append(" ");
        }

        return st.toString();
    }

    private String formatearLista()
    {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < this.inscritos.size(); i++)
        {
            st.append(this.inscritos.get(i));
            if(i<  this.inscritos.size()-1)
            {
                st.append("/");
            }
        }

        return  st.toString();
    }
}
