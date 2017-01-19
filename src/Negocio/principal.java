

package Negocio;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import sun.audio.*;


public class principal extends JFrame{
    
    private JButton cmdIniciar;
    private JButton cmdReiniciar;
    private medidor flecha;
    private ActionListener accionIniciar;
    
    public principal(){
        setSize(435,540);
        setLocationRelativeTo(null);
        setResizable( false );
        setLayout(null);
        flecha=new medidor();
        flecha.setBounds(0,0,435,540);
        add(flecha);
        cmdIniciar=new JButton("Iniciar");
        
        cmdReiniciar=new JButton("Reiniciar");
        cmdReiniciar.setBounds(0,0,100,30);
        cmdReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               flecha.reiniciar();
               flecha.setIntento(flecha.getIntento()+1);
               flecha.setNormal(false);
               flecha.setVariable(flecha.getMalo());
            }
        });
        cmdIniciar.setBounds(0,0,100,30);
        cmdIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flecha.start();
                cmdIniciar.setVisible(false);
                add(cmdReiniciar);
            }
        });
        add(cmdIniciar);
    }
    
    
    public static void main(String[] args) {
        principal principal=new principal();
        principal.setVisible(true);
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}

 class medidor extends JPanel{
    
    private javax.swing.Timer t;
    private int variable=625;
    private int alto=438;
    private int ancho=410;
    private int tiempo=140;
    private boolean normal=false;
    private int malo=625;
    private int bueno=-50;
    private int masOmenos=300;
    private int intento=1;
    private Graphics2D g2;
    private int cuadro=0;
    private boolean sonar=true;
    private int cuantosMalos=4;
    
    public medidor(){
        setLayout(null);
        t = new javax.swing.Timer(tiempo,new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                        if (intento!=cuantosMalos) {
                          if (variable <= masOmenos && !normal) {
                              variable = masOmenos;
                              normal = true;
                              cuadro=0;
                              tiempo=140;
                              sonido("src/sonidos/silvido 3.wav");
                          } else if (normal && variable != malo) {
                              variable += 25;
                              cuadro=0;
                          } else if (variable == malo && normal) {
                              variable = malo;
                              cuadro=1;
                              if(!sonar){
                                  sonido("src/sonidos/mal 1.wav");
                                  sonar=true;
                              }
                          } else {
                              variable -= 25;
                              cuadro=0;
                              if(sonar){
                                  sonido("src/sonidos/silvido 2.wav");
                                  sonar=false;
                              }
                          }
                      }else{
                            if(variable==masOmenos&&!normal){
                                variable=masOmenos;
                                normal=true;
                                cuadro=0;
                                tiempo=25;
                            }else if(normal&&variable!=bueno){
                                variable-=25;
                                cuadro=0;
                                if(!sonar){
                                    sonido("src/sonidos/silvidoconcampana.wav");
                                    sonar=true;
                               }
                            }else if(variable==bueno&&normal){
                                variable=bueno;
                                cuadro=2;
                            }else{
                                variable-=25;
                                cuadro=0;
                                if(sonar){
                                    sonido("src/sonidos/silvido 2.wav");
                                    sonar=false;
                              }
                            }
                       }
                      update();  
                  }
              }); 
        
    }
    
    
    
     public void sonido(String direccion) {
         try {
             InputStream in = new FileInputStream(direccion);
             AudioStream audio = new AudioStream(in);
             AudioPlayer.player.start(audio);
         } catch (Exception ex) {
             ex.printStackTrace();
         }
     }
    
     public void paintComponent(Graphics g) {
         super.paintComponents(g);
          g2 = (Graphics2D) g;
         fondo(g);
         flecha(g);
         
     }

     public void flecha(Graphics g) {
         g2.rotate(variable, ancho / 2, alto / 2);
         g2.drawImage(new ImageIcon("src/imagenes/flechaNegra.png").getImage(), (ancho / 2) - 70, (alto / 2) - 15, 170, 30, null);
     }

     public void fondo(Graphics g) {
         ImageIcon imagen = new ImageIcon("src/imagenes/tacometro.png");
         g2.drawImage(imagen.getImage(), 5, 5, imagen.getIconWidth(), imagen.getIconHeight(), null);
         if(cuadro==1){
           ImageIcon imagen2 = new ImageIcon("src/imagenes/no es suficiente.png");
         g2.drawImage(imagen2.getImage(), 5, 410, imagen2.getIconWidth(), imagen2.getIconHeight(), null);  
         }else if(cuadro==2){
             ImageIcon imagen2 = new ImageIcon("src/imagenes/suficientemente bueno.png");
         g2.drawImage(imagen2.getImage(), 5, 410, imagen2.getIconWidth(), imagen2.getIconHeight(), null);
         }else if(cuadro==0){
             g2.setColor(Color.black);
             g2.fillRect(5, 410, 420, 100);
         }
     }
    public void update(){
        this.repaint();
    }
    public void start() {
        t.start();
    }
    public void reiniciar(){
        t.restart();
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public int getIntento() {
        return intento;
    }

    public void setIntento(int intento) {
        this.intento = intento;
    }

    public int getMalo() {
        return malo;
    }

}