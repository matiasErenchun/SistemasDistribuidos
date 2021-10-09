package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Cliente
{
    private final String host = "127.0.0.1";
    private final int puerto = 5000;
    private DataOutputStream salida;
    private Socket socket;
    private InputStream entrada;
    private DataInputStream flujo;

    public Cliente()
    {
        try
        {

            boolean continuar = true;
            Scanner scanner = new Scanner(System.in);
            while (continuar)
            {
                this.socket = new Socket(this.host,this.puerto);
                this.salida = new DataOutputStream(this.socket.getOutputStream());
                System.out.println(" ingrese 1 para agregar un nombre, pingrese dos para ver la lista y 3 para terminar las ejecuciones");
                System.out.println("ingrese su opcion");
                String mensaje ="";
                String opcion = scanner.nextLine();
                if(opcion.equals("1"))
                {
                    mensaje = opcion + " ";
                    System.out.println("ingrese el nombre a agregar");
                    String nombre = scanner.nextLine();
                    mensaje = mensaje + nombre;
                }
                else if (opcion.equals("3"))
                {
                    continuar = false;
                    mensaje = opcion;
                }
                else
                {
                    mensaje = opcion;
                }
                this.salida.writeUTF(mensaje);
                this.entrada = this.socket.getInputStream();
                this.flujo = new DataInputStream(this.entrada);
                String lista =this.flujo.readUTF();
                StringTokenizer tokenLista = new StringTokenizer(lista,"/");
                while (tokenLista.hasMoreTokens())
                {
                    System.out.println(tokenLista.nextToken());
                }
                this.socket.close();
            }

        }
        catch (Exception e)
        {

        }

    }
}
