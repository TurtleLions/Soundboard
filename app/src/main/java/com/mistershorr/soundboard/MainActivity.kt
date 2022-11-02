package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mistershorr.soundboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var soundPool : SoundPool
    var aNote = 0
    var bbNote = 0
    var bNote = 0
    var cNote = 0
    var csNote = 0
    var dNote = 0
    var dsNote = 0
    var eNote = 0
    var fNote = 0
    var fsNote = 0
    var gNote = 0
    var gsNote = 0
    var lgNote = 0

    private lateinit var binding: ActivityMainBinding

    lateinit var songStorage: List<Note>
    var songTest = "A 0 C 500 C 500 G 500 G 500 A 500 A 500 G 1000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeSoundPool()
        setListeners()
        importSong()
        var songGet = parser(songTest)
        playSong(songGet)
    }

    private fun setListeners() {
        val soundBoardListener = SoundBoardListener()
        binding.buttonMainA.setOnClickListener(soundBoardListener)
        binding.buttonMainBb.setOnClickListener(soundBoardListener)
        binding.buttonMainB.setOnClickListener(soundBoardListener)
        binding.buttonMainC.setOnClickListener(soundBoardListener)
        binding.buttonMainCs.setOnClickListener(soundBoardListener)
        binding.buttonMainD.setOnClickListener(soundBoardListener)
        binding.buttonMainDs.setOnClickListener(soundBoardListener)
        binding.buttonMainE.setOnClickListener(soundBoardListener)
        binding.buttonMainF.setOnClickListener(soundBoardListener)
        binding.buttonMainFs.setOnClickListener(soundBoardListener)
        binding.buttonMainG.setOnClickListener(soundBoardListener)
        binding.buttonMainGs.setOnClickListener(soundBoardListener)
    }


    private fun initializeSoundPool() {

        this.volumeControlStream = AudioManager.STREAM_MUSIC
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
//        soundPool.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
//           // isSoundPoolLoaded = true
//        })
        aNote = soundPool.load(this, R.raw.scalea, 1)
        bbNote = soundPool.load(this, R.raw.scalebb, 1)
        bNote = soundPool.load(this, R.raw.scaleb, 1)
        cNote =  soundPool.load(this, R.raw.scalec, 1)
        csNote =  soundPool.load(this, R.raw.scalecs, 1)
        dNote =  soundPool.load(this, R.raw.scaled, 1)
        dsNote =  soundPool.load(this, R.raw.scaleds, 1)
        eNote =  soundPool.load(this, R.raw.scalee, 1)
        fNote =  soundPool.load(this, R.raw.scalef, 1)
        fsNote =  soundPool.load(this, R.raw.scalefs, 1)
        gNote =  soundPool.load(this, R.raw.scaleg, 1)
        gsNote =  soundPool.load(this, R.raw.scalegs, 1)
        gsNote =  soundPool.load(this, R.raw.scalegs, 1)
        lgNote = soundPool.load(this, R.raw.scalelowg, 1)
    }

    private fun playNote(noteId : Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }

    private inner class SoundBoardListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when(v?.id) {
                R.id.button_main_a -> playNote(aNote)
                R.id.button_main_bb -> playNote(bbNote)
                R.id.button_main_b -> playNote(bNote)
                R.id.button_main_c -> playNote(cNote)
                R.id.button_main_cs -> playNote(csNote)
                R.id.button_main_d -> playNote(dNote)
                R.id.button_main_ds -> playNote(dsNote)
                R.id.button_main_e -> playNote(eNote)
                R.id.button_main_f -> playNote(fNote)
                R.id.button_main_fs -> playNote(fsNote)
                R.id.button_main_g -> playNote(gNote)
                R.id.button_main_gs -> playNote(gsNote)
            }
        }
    }

    private fun importSong(){
        val inputStream = resources.openRawResource(R.raw.song)
        val jsonString = inputStream.bufferedReader().use{
            it.readText()
        }
        val gson = Gson()
        val type = object : TypeToken<List<Note>>() {}.type
        songStorage = gson.fromJson(jsonString, type)
    }

    private fun parser(input: String): List<Note>{
        var songParsed = mutableListOf<Note>()
        var note: String = ""
        var duration = ""
        var onNote = true
        for(index in input.indices){
            val character: String = input[index].toString()
            val nextLetter = input.getOrNull(index + 1)
            if(onNote&&character!=" "){
                note+=character
            }
            else if(character!=" "){
                duration+=character
            }
            if(nextLetter==null||nextLetter == ' '){
                if(onNote){
                    onNote=false
                }
                else{
                    onNote=true
                    var newNote = Note(duration.toInt(), note)
                    songParsed.add(newNote)
                    note = ""
                    duration = ""
                }
            }
        }
        return songParsed
    }
    private fun playSong(song: List<Note>){
        for(note in song){
            when(note.note) {
                "A" -> playNote(aNote)
                "BB" -> playNote(bbNote)
                "B" -> playNote(bNote)
                "C" -> playNote(cNote)
                "CS" -> playNote(csNote)
                "D" -> playNote(dNote)
                "DS" -> playNote(dsNote)
                "E" -> playNote(eNote)
                "F" -> playNote(fNote)
                "FS" -> playNote(fsNote)
                "G" -> playNote(gNote)
                "GS" -> playNote(gsNote)
            }
            Thread.sleep(note.duration.toLong())
        }
    }
}