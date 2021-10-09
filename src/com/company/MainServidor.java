package com.company;

public class MainServidor
{
    public static Servidor servidor;
    public static void main(String[] args)
    {
        servidor = new Servidor();
        servidor.iniciar();
    }
}
