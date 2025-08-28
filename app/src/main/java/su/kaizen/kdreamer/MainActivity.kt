package su.kaizen.kdreamer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.TimeFormatException
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.sql.Time
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)

class MainActivity : AppCompatActivity() {
    lateinit var input: TextInputEditText;
    lateinit var calculateBtn: Button;
    lateinit var nowBtn: Button;
    lateinit var hourTxt: TextView;
    lateinit var format: DateTimeFormatter;
    lateinit var statusTxt: TextView;
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
        statusTxt = findViewById(R.id.statusText);
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
        try{
            if (nowPressed) {
                parsedTime = getActualTime();
            }
            else{
                time = input.text.toString();
                parsedTime = LocalTime.parse(time,format);
            }
            showError(false);
            var txt2show = "";
            for(i in 1..6){
                if(i == 4){
                    txt2show += "--- Optimal ---\n"
                }
                parsedTime = parsedTime.plusMinutes(90);
                txt2show += "Cycle "+i+": "+parsedTime.format(format)+"\n";
            }
            hourTxt.text = txt2show;
        }
        catch(ex: DateTimeParseException){
            showError(true);
            input.setText("");
            hourTxt.setText("");
        }


    }

    fun showError(toggle: Boolean){
        if(toggle){
            statusTxt.setText("Please enter a valid time (hh:mm) in 24h format.");
        }
        else{
            statusTxt.setText("");
        }

    }

    // I copied this from StackOverflow lol
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    @SuppressLint("ServiceCast")
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}