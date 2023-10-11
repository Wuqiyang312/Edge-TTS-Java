/*
 * Created by JFormDesigner on Fri Jun 23 12:53:24 CST 2023
 */

package webside.wuqingyuan.TTS.ui;

import java.awt.event.*;
import javax.swing.event.*;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import webside.wuqingyuan.TTS.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author 13776
 */
public class MainGui extends JFrame {
    private static final OkHttpClient client = new OkHttpClient();
    ArrayList<String> ShortNames = new ArrayList<>();
    public MainGui() throws IOException {
        initComponents();
        // 获取可用语音包选项
        String voicesUrl = "https://speech.platform.bing.com/consumer/speech/synthesize/readaloud/voices/list?trustedclienttoken=6A5AA1D4EAFF4E9FB37E23D68491D6F4";
        Request voicesRequest = new Request.Builder()
                .url(voicesUrl)
                .get()
                .build();
        // 执行获取可用语音包选项的请求
        try (Response response = client.newCall(voicesRequest).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                // 处理获取到的可用语音包选项数据
                Gson gson = new Gson();
                ETTS[] voiceOptions = gson.fromJson(responseData, ETTS[].class);
                for (ETTS voiceOption:voiceOptions){
                    int x=0;
                    for (String shortName:ShortNames) {
                        if (Objects.equals(voiceOption.getShortName(), shortName)){
                            x++;
                        }
                    }
                    if (x == 0) {
                        ShortNames.add(voiceOption.getShortName());
                    }
                }
                for (String shortName:ShortNames) {
                    ShortName.addItem(shortName);
                }
            } else {
                // 处理请求失败
                System.err.println("处理请求失败");
            }
        }
        ShortName.setSelectedIndex(55);
        // 保存位置设置
        textSave.setText(Constant.desktopPath);
    }

    private void ShortName(ActionEvent e) {
        String itemShortName = (String)ShortName.getSelectedItem();
        String[] items = itemShortName.split("-");
        Name.setText("Microsoft Server Speech Text to Speech Voice ("+items[0]+"-"+items[1]+", "+items[2]+")");
    }

    private void slider_speedStateChanged(ChangeEvent e) {
        double value = slider_speed.getValue();
        v_speed.setText(String.valueOf(value/10));
    }

    private void btn_run(ActionEvent e) {
        String itemShortName = (String)ShortName.getSelectedItem();
        String[] items = itemShortName.split("-");
        Thread thread = new Thread(new TTS(items[0]+"-"+items[1],String.valueOf(Name.getText())
                , TTS_text.getText(), v_speed.getText()));
        thread.start();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!thread.isAlive()) {
                    btn_try.setEnabled(true);
                    btn_save.setEnabled(true);
                    timer.cancel();
                }
            }
        },0,1000);
    }

    private void TTS_textCaretUpdate(CaretEvent e) {
        btn_try.setEnabled(false);
        btn_save.setEnabled(false);
    }

    private void btn_try(ActionEvent e) {
        new WebMAudioPlayer();
    }

    private void btn_save(ActionEvent e) {
        new SaveMp3();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        label1 = new JLabel();
        label3 = new JLabel();
        ShortName = new JComboBox();
        Name = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        slider_speed = new JSlider();
        v_speed = new JLabel();
        label8 = new JLabel();
        textSave = new JTextField();
        panel2 = new JPanel();
        scrollPane1 = new JScrollPane();
        TTS_text = new JTextArea();
        btn_try = new JButton();
        btn_save = new JButton();
        btn_run = new JButton();

        //======== this ========
        setTitle("\u5fae\u8f6fTTS");
        setResizable(false);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                //---- label1 ----
                label1.setText("\u8bed\u97f3\u4eba\u7269\u9009\u62e9:");

                //---- label3 ----
                label3.setText("\u4f5c\u8005:\u4e4c\u6e05\u8fdc");

                //---- ShortName ----
                ShortName.addActionListener(e -> ShortName(e));

                //---- Name ----
                Name.setText("\u8bed\u97f3\u4eba\u7269");

                //---- label4 ----
                label4.setText("\u8bed\u97f3\u4eba\u7269\u9009\u62e9:");

                //---- label5 ----
                label5.setText("\u901f\u5ea6:");

                //---- slider_speed ----
                slider_speed.setMaximum(20);
                slider_speed.setValue(10);
                slider_speed.addChangeListener(e -> slider_speedStateChanged(e));

                //---- v_speed ----
                v_speed.setText("1.0");

                //---- label8 ----
                label8.setText("\u9ed8\u8ba4\u4fdd\u5b58:");

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addComponent(label1)
                                    .addGap(12, 12, 12)
                                    .addComponent(ShortName)
                                    .addGap(18, 18, 18))
                                .addComponent(label4, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                                .addComponent(Name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addComponent(label3)
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textSave)
                                            .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(label5)
                                                .addGap(18, 18, 18)
                                                .addComponent(slider_speed, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(v_speed)))
                                        .addComponent(label8, GroupLayout.PREFERRED_SIZE, 468, GroupLayout.PREFERRED_SIZE))))
                            .addGap(0, 12, Short.MAX_VALUE))
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(ShortName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(27, 27, 27)
                            .addComponent(label4)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Name, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(slider_speed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(v_speed))
                            .addGap(32, 32, 32)
                            .addComponent(label8)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textSave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(74, 74, 74)
                            .addComponent(label3))
                );
            }
            tabbedPane1.addTab("\u57fa\u7840\u8bbe\u7f6e", panel1);

            //======== panel2 ========
            {

                //======== scrollPane1 ========
                {

                    //---- TTS_text ----
                    TTS_text.addCaretListener(e -> TTS_textCaretUpdate(e));
                    scrollPane1.setViewportView(TTS_text);
                }

                //---- btn_try ----
                btn_try.setText("\u8bd5\u542c");
                btn_try.setEnabled(false);
                btn_try.addActionListener(e -> btn_try(e));

                //---- btn_save ----
                btn_save.setText("\u4fdd\u5b58");
                btn_save.setEnabled(false);
                btn_save.addActionListener(e -> btn_save(e));

                //---- btn_run ----
                btn_run.setText("\u8fd0\u884c");
                btn_run.addActionListener(e -> btn_run(e));

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel2Layout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addComponent(btn_save)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_try)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
                                    .addComponent(btn_run)))
                            .addContainerGap())
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_save)
                                .addComponent(btn_try)
                                .addComponent(btn_run))
                            .addContainerGap(10, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("\u6587\u5b57TTS", panel2);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane1)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane1)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JLabel label1;
    private JLabel label3;
    private JComboBox ShortName;
    private JLabel Name;
    private JLabel label4;
    private JLabel label5;
    private JSlider slider_speed;
    private JLabel v_speed;
    private JLabel label8;
    private JTextField textSave;
    private JPanel panel2;
    private JScrollPane scrollPane1;
    private JTextArea TTS_text;
    private JButton btn_try;
    private JButton btn_save;
    private JButton btn_run;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
