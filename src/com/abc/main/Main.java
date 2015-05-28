/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc.main;

import bayesclassPrivacy.Crypto;
import com.abc.connect.DBConnect;
import com.abc.dao.tadDAO;
import com.abc.entity.tad;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MANHKHUC
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://192.168.124.99/KMA";
    //  Database credentials
    static final String USER = "tad";
    static final String PASS = "123456";
    static String outlook[] = new String[10000];
    static String temperature[] = new String[10000];
    static String humidity[] = new String[10000];
    static String windy[] = new String[10000];
    static String class1[] = new String[10000];
    static String tableDB[][] = new String[10000][5];
    static int[][] DTBplay, DTBnoplay;
    static int[] CountDPlay, CountDnoPlay;
    static int x[][] = new int[10000][10];
    static int y[][] = new int[10000][10];
    static BigInteger m[] = new BigInteger[14];
    static BigInteger h[] = new BigInteger[14];
    static double d[][] = new double[10000][10];
    static BigInteger r = new BigInteger("1");
    static BigInteger r1 = new BigInteger("1");
    static BigInteger[][] m0, m1, h0, h1;
    static BigInteger g;
    static BigInteger X[][] = new BigInteger[10000][5];
    static BigInteger Y[][] = new BigInteger[10000][5];
    static BigInteger[] Xsum, X0sum, X1sum;
    static BigInteger[] Ysum, Y0sum, Y1sum;
    static int countU = 0;
    static int NumReC; //NumReC= Number Records = countU
    static int NumColumn;
    static double NumPlay;
    static double NumNoPlay;
    static double prob[][] = new double[4][2];
    static double prob1[][] = new double[4][2];
    static double pp;
    static double npp;
    static int flag = 0;
    static int flag1 = 0;
    static double play_N = 1;
    static double notplay_N = 1;
    private DefaultTableModel model;

    public Main() {
        initComponents();
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Outlook");
        model.addColumn("Temperature");
        model.addColumn("Humidity");
        model.addColumn("Windy");
        model.addColumn("Class");
        jTable1.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Show");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(162, 162, 162))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        List<tad> list = new ArrayList<tad>();
        list = tadDAO.ListTad();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        for (tad t : list) {
            model.addRow(new String[]{t.getId() + "", t.getOutlook(), t.getTemperature(), t.getHumidity(), t.getWindy(), t.getClasses()});
        }
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                //        Connection conn = null;
                Statement stmt = null;
                NumColumn = 5;
                Xsum = Crypto.DefaultONE(Xsum, NumColumn);
                Ysum = Crypto.DefaultONE(Ysum, NumColumn);
                g = Crypto.RandomBigInt(3);




                try {
                    //STEP 2: Register JDBC driver

//            conn = DBConnect.openConnection("tad", "123456");
                    ResultSet rs = tadDAO.ListRs();
                    //STEP 5: Extract data from result set
                    System.out.println("Table\n");
                    System.out.println("ID\t   Outlook\t   Temperature\t    Humidity\t      Windy     \tClass");

                    while (rs.next()) {
                        //Retrieve by column name
                        for (int i = 0; i < 5; ++i) {
                            x[countU][i] = Crypto.RandomInt(5);
                            y[countU][i] = Crypto.RandomInt(5);
                            X[countU][i] = g.pow(x[countU][i]);
                            Y[countU][i] = g.pow(y[countU][i]);
                            Xsum[i] = Xsum[i].multiply(X[countU][i]);
                            Ysum[i] = Ysum[i].multiply(Y[countU][i]);
                        }

                        int id = rs.getInt("id");
                        tableDB[countU][0] = outlook[countU] = rs.getString("Outlook");
                        tableDB[countU][1] = temperature[countU] = rs.getString("Temperature");
                        tableDB[countU][2] = humidity[countU] = rs.getString("Humidity");
                        tableDB[countU][3] = windy[countU] = rs.getString("Windy");
                        tableDB[countU][4] = class1[countU] = rs.getString("Class");
                        System.out.print(id + "\t\t" + outlook[countU] + "\t\t" + temperature[countU] + "\t\t" + humidity[countU] + "\t\t" + windy[countU] + "\t\t" + class1[countU] + countU);
                        System.out.println();
                        ++countU;

                    }
                    //STEP 6: Clean-up environment
                    rs.close();
                    //}

//            conn.close();
                } catch (SQLException se) {
                    //Handle errors for JDBC
                    se.printStackTrace();
                } catch (Exception e) {
                    //Handle errors for Class.forName
                    e.printStackTrace();
                } finally {
                    //finally block used to close resources
                    try {
                        if (stmt != null) {
                            stmt.close();
                        }
                    } catch (SQLException se2) {
                    }// nothing we can do
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }//end finally try
                }//end try
                System.out.println("Goodbye!");
//close database
                NumReC = countU; //NumReC= Number Records = 14 in this case 


                System.out.println("Goodbye!" + NumReC);
                System.out.println("Menu:\nOutlook: Sunny=S Overcast=O Rain=R\tTemperature: Hot=H Mild=M Cool=C\n");
                System.out.println("Humidity: Peak=P Normal=N\t\tWindy: True=T False=F\n\nYour input should belong to one of these classes.\n");
                System.out.println("class1: Play=P   class2:Not Play=NP");
                Scanner scr = new Scanner(System.in);
                System.out.println("\nEnter your input: example. t={rain,hot,peak,false} input will be R,H,P,F");
                String ch;
                String S[] = new String[9];
                for (int i = 0; i < 4; ++i) {
                    System.out.println("Nhap gia trị " + (i + 1));
                    S[i] = scr.nextLine();
                }


                int d1[] = Count_classVL(class1, "P", NumReC);
                DTBplay = Count_D(tableDB, S, "P", 5, NumReC);
                DTBnoplay = Count_D(tableDB, S, "N", 5, NumReC);
                m0 = Crypto.EcryptionDM(DTBplay, Xsum, y, g, 5, NumReC);
                h0 = Crypto.EcryptionDH(DTBplay, Ysum, x, g, 5, NumReC);
                CountDPlay = Crypto.DecryptionHM(m0, h0, g, 5, NumReC);

                m1 = Crypto.EcryptionDM(DTBnoplay, Xsum, y, g, 5, NumReC);
                h1 = Crypto.EcryptionDH(DTBnoplay, Ysum, x, g, 5, NumReC);
                CountDnoPlay = Crypto.DecryptionHM(m1, h1, g, 5, NumReC);

                for (int i = 0; i < 14; ++i) {
                    for (int j = 0; j < 5; ++j) {
                        //System.out.println("h[ "+i+j+ "] "+DTBplay[i][j] );
                        System.out.print("\td " + DTBplay[i][j]);
                    }
                    System.out.println();
                }

                //--------------------

                NumPlay = (double) CountDPlay[4];
                NumNoPlay = (double) CountDnoPlay[4];
                pp = (double) NumPlay / NumReC;
                npp = (double) NumNoPlay / NumReC;

                for (int i = 0; i < 4; ++i) {


                    prob[i][0] = CountDPlay[i] / NumPlay;
                    prob[i][1] = CountDnoPlay[i] / NumNoPlay;
                    System.out.println(" count99=" + i + "  ky tu99 =" + CountDPlay[i] + "  prob99[" + i + "][0]=" + prob1[i][0] + "  prob99[i][1]=" + prob1[i][1]);


                }

                cal_N(1);
                cal_N(2);

                double pt = play_N + notplay_N;
                System.out.println("play_N+notplay_N :" + play_N + "+" + notplay_N);
                System.out.println("pt =" + pt);
                double prob_of_play = 0;
                double prob_of_noplay = 0;

                prob_of_play = play_N / pt;
                prob_of_noplay = notplay_N / pt;

                System.out.println("\nProbability of play " + prob_of_play);
                System.out.println("\nProbability of NO play " + prob_of_noplay);


                if (prob_of_play > prob_of_noplay) {
                    System.out.println("\nThe new tuple classified under \"PLAY\" category.Hence there will be play!!!");
                } else {
                    System.out.println("\nThe new tuple classified under \"NO PLAY\" category.Hence there will be NO play.");
                }

            }

            
        };
        new Thread(runnable).start();

    }//GEN-LAST:event_jButton1ActionPerformed
    static int[] Count_classVL(String a[], String b, int n) {
                int d1[] = new int[n];
                for (int i = 0; i < n; ++i) {
                    if (a[i].equals(b)) {
                        d1[i] = 1;
                    } else {
                        d1[i] = 0;
                    }
                    // System.out.println("\ndi "+ d[i][0]);
                }
                return d1;
            }

            static int[][] Count_D(String tableDB[][], String stadin[], String b, int column, int num) {
                int d[][] = new int[num][column];

                for (int j = 0; j < num; ++j) {
                    for (int i = 0; i < column - 1; ++i) {

                        if (tableDB[j][i].equals(stadin[i]) && tableDB[j][column - 1].equals(b)) {
                            d[j][i] = 1;
                        } else {
                            d[j][i] = 0;
                        }
                        if (tableDB[j][column - 1].equals(b)) {
                            d[j][column - 1] = 1;
                        } else {
                            d[j][column - 1] = 0;
                        }
                    }
                }
                return d;
            }

            static void cal_N(int a) {
                if (a == 1) {
                    for (int i = 0; i < 4; ++i) {
                        play_N *= prob[i][0];
                    }
                    System.out.println("\n11Value of N of play \n" + play_N);
                    play_N *= pp;
                    System.out.println("\nValue of N of play \n" + play_N);
                } else {
                    for (int i = 0; i < 4; ++i) {
                        notplay_N *= prob[i][1];
                    }
                    System.out.println("\n11Value of N of play \n" + play_N);
                    notplay_N *= npp;
                    System.out.println("\nValue of N of No play \n" + notplay_N);
                }

            }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;


                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
