package david.ferrer.progressbar.models

data class CheckInfo (
    val title : String,
    var selected: Boolean = false,
    var onChange: (Boolean) -> Unit
)