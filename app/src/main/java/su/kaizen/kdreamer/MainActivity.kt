package su.kaizen.kdreamer

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import java.sql.Time
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)

class MainActivity : AppCompatActivity() {
    lateinit var input: TextInputEditText;
    lateinit var calculateBtn: Button;
    lateinit var nowBtn: Button;
    lateinit var hourTxt: TextView;
    lateinit var time: LocalTime;
    lateinit var format: DateTimeFormatter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        input = findViewById(R.id.inputTime);
        calculateBtn = findViewById(R.id.calcButton);
        nowBtn = findViewById(R.id.nowButton);
        hourTxt = findViewById(R.id.hourText);
        format = DateTimeFormatter.ofPattern("HH:mm");

        calculateBtn.setOnClickListener{
            getHourNShow(false);
        }
        nowBtn.setOnClickListener{
            getHourNShow(true);
        }
    }

    fun getActualTime(): LocalTime{
        return LocalTime.now();
    }

    fun getHourNShow(nowPressed: Boolean){
        var time: String;
        var parsedTime: LocalTime;
        if (nowPressed) {
            parsedTime = getActualTime();
        }
        else{
            time = input.text.toString();
            parsedTime = LocalTime.parse(time,format);
        }

        var txt2show = "";
        for(i in 1..8){
            parsedTime = parsedTime.plusMinutes(90);
            txt2show += "Cycle "+i+": "+parsedTime.format(format)+"\n";
        }
        hourTxt.text = txt2show;
    }
}