package Red;

import Estados.EstadoJuegoOnline;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Servidor extends Thread{

    private EstadoJuegoOnline estadoJuegoOnline;

    public Servidor(EstadoJuegoOnline ejo){
        this.estadoJuegoOnline=ejo;
    }

    @Override
    public void run() {
        try(ServerSocket ss=new ServerSocket(9990)){
            Executor pool = Executors.newCachedThreadPool();
            while(!this.estadoJuegoOnline.getComunicador().finDeComunicacion){
                try{
                    Socket s=ss.accept();
                    pool.execute(new AtenderPeticionPartida(this.estadoJuegoOnline,s));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
