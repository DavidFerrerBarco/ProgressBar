package david.ferrer.progressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import david.ferrer.progressbar.models.CheckInfo
import david.ferrer.progressbar.ui.theme.ProgressBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val titles = listOf("Adrian", "Salva", "Miriam", "Mario", "Vadim")
            ProgressBarTheme {
                MyProgressBar()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    val titles = listOf("Adrian", "Salva", "Miriam", "Mario", "Vadim")
    ProgressBarTheme {
        Column(
            Modifier.fillMaxSize()
        ) {
            CreateCheckBoxes(titles = titles).forEach {
                MyCheckBox(checkInfo = it)
            }
        }
    }
}

@Composable
fun MyProgressBar(){
    var showloading by rememberSaveable {
        mutableStateOf(false)
    }
    var processStatus by rememberSaveable {
        mutableStateOf(0f)
    }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        if(showloading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 3.dp,
                progress = processStatus
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Green,
                backgroundColor = Color.LightGray,
                progress = processStatus
            )
            Row(
                Modifier.padding(top = 8.dp),
                Arrangement.SpaceAround,
                Alignment.CenterVertically
            ){
                Button(
                    onClick ={
                        if(processStatus > 0f && (processStatus-0.1f) > 0f)
                            processStatus -= 0.1f
                    }) {
                    Text(text = "Reducir")
                }
                Button(
                    onClick = {
                        if(processStatus < 1f)
                            processStatus += 0.1f
                        else
                            processStatus = 0f
                    }
                ) {
                    Text(text = "Incrementar")
                }
            }
        }


        Button(
            modifier = Modifier.padding(top = 100.dp),
            onClick = { showloading = !showloading }
        ) {
            Text(text = "Activar / Desactivar")
        }

    }
}

@Composable
fun MyCheckBox(checkInfo: CheckInfo){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Checkbox(
            checked = checkInfo.selected,
            onCheckedChange = {checkInfo.onChange(!checkInfo.selected)}
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = checkInfo.title)
    }
}

@Composable
fun CreateCheckBoxes(titles: List<String>): List<CheckInfo>{
    return titles.map {
        var status by rememberSaveable { mutableStateOf(false) }
        CheckInfo(
            title = it,
            selected = status,
            onChange = { status = it}
        )
    }
}

@Composable
fun MyTriStatusCheckBox(){
    var status by rememberSaveable { mutableStateOf(ToggleableState.Off) }
    TriStateCheckbox(state = status, onClick = {
        status = when(status){
            ToggleableState.On -> ToggleableState.Off
            ToggleableState.Off -> ToggleableState.Indeterminate
            ToggleableState.Indeterminate -> ToggleableState.On
        }
    })
}
