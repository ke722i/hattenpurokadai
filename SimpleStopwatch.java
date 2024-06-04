import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer; //必ずswing.Timerを使う。start(), stop(), restart()などのメソッドが使えるから。

public class SimpleStopwatch {
    
    public void perform() {

        EventQueue.invokeLater(new Runnable() { //正しい順にEventが実行するかどうかを確認
            @Override
            public void run() {
                //ウィンドウの作成
                JFrame mainFrame = new JFrame("SimpleStopwatch");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.add(new Window());
                mainFrame.pack();
                mainFrame.setSize(400 , 300);
                mainFrame.setVisible(true);
            }
        });

    }

    public class Window extends JPanel {
        
        private LocalDateTime startTime; //startTime初期化
        private JLabel label; //label初期化
        private Timer timer; //timer初期化

        public Window() {

            setLayout(new GridBagLayout()); //GridBagLayoutは格子のサイズを柔軟に設定できるレイアウト。
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = GridBagConstraints.REMAINDER;

            label = new JLabel("SimpleStopwatch"); //Labelの設定
            add(label, gbc);

            //ボタンの作成
            JButton startButton = new JButton("Start");
            //startボタンにアクションを設定
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(timer.isRunning()) { 
                        timer.stop();
                        startTime = null;
                        startButton.setText("Start");
                    } else {
                        startTime = LocalDateTime.now();
                        timer.start();
                        startButton.setText("Stop");
                    }
                }
            });
            add(startButton,gbc);

            timer = new Timer(1000, new ActionListener() { //Timerの設定
               @Override
               public void actionPerformed(ActionEvent e) {
                    LocalDateTime now = LocalDateTime.now();
                    Duration duration = Duration.between(startTime, now);
                    label.setText(format(duration));
                } 
            });
        }

        protected String format(Duration duration) { //時間、分間、秒間の設定
            long hours = duration.toHours();
            long mins = duration.minusHours(hours).toMinutes();
            long seconds = duration.minusMinutes(mins).toMillis() / 1000;
            return String.format("%02dh %02dm %02ds", hours, mins, seconds);
        }
    }
}
//こうりつてきでよかった　福井　柊弥
//プログラムの組み方が見やすかった　山本剛希