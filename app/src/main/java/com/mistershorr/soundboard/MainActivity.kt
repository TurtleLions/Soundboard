package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import kotlinx.coroutines.*
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
    var haNote = 0
    var hbbNote = 0
    var hbNote = 0
    var hcNote = 0
    var hcsNote = 0
    var hdNote = 0
    var hdsNote = 0
    var heNote = 0
    var hfNote = 0
    var hfsNote = 0
    var hgNote = 0
    var hgsNote = 0

    var noteMap = HashMap<String, Int>()

    private lateinit var binding: ActivityMainBinding

    lateinit var songStorage: List<Note>
    var songTest = ""

    //Song Examples
    //Mary Had a Little Lamb: B 500 A 500 LG 500 A 500 B 500 B 500 B 1000 A 500 A 500 A 1000 B 500 D 500 D 1000 B 500 A 500 LG 500 A 500 B 500 B 500 B 500 B 500 A 500 A 500 B 500 A 500 LG 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeSoundPool()
        setListeners()
        importSong()
        var songGet = parser(songTest)
        GlobalScope.launch {
            playSong(songGet)
        }
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
        soundPool = SoundPool(100, AudioManager.STREAM_MUSIC, 0)
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
        lgNote = soundPool.load(this, R.raw.scalelowg, 1)
        haNote = soundPool.load(this, R.raw.scalehigha, 1)
        hbbNote = soundPool.load(this, R.raw.scalehighbb, 1)
        hbNote = soundPool.load(this, R.raw.scalehighb, 1)
        hcNote =  soundPool.load(this, R.raw.scalehighc, 1)
        hcsNote =  soundPool.load(this, R.raw.scalehighcs, 1)
        hdNote =  soundPool.load(this, R.raw.scalehighd, 1)
        hdsNote =  soundPool.load(this, R.raw.scalehighds, 1)
        heNote =  soundPool.load(this, R.raw.scalehighe, 1)
        hfNote =  soundPool.load(this, R.raw.scalehighf, 1)
        hfsNote =  soundPool.load(this, R.raw.scalehighfs, 1)
        hgNote =  soundPool.load(this, R.raw.scalehighg, 1)
        hgsNote =  soundPool.load(this, R.raw.scalehighgs, 1)

        noteMap.put("A", aNote)
        noteMap.put("BB", bbNote)
        noteMap.put("B", bNote)
        noteMap.put("C", cNote)
        noteMap.put("D", dNote)
        noteMap.put("DS", dsNote)
        noteMap.put("E", eNote)
        noteMap.put("F", fNote)
        noteMap.put("FS", fsNote)
        noteMap.put("G", gNote)
        noteMap.put("GS", gsNote)
        noteMap.put("LG", lgNote)
        noteMap.put("HA", haNote)
        noteMap.put("HBB", hbbNote)
        noteMap.put("HB", hbNote)
        noteMap.put("HC", hcNote)
        noteMap.put("HCS", hcsNote)
        noteMap.put("HD", hdNote)
        noteMap.put("HDS", hdsNote)
        noteMap.put("HE", heNote)
        noteMap.put("HF", hfNote)
        noteMap.put("HFS", hfsNote)
        noteMap.put("HG", hgNote)
        noteMap.put("HGS", hgsNote)

    }

    private fun playNote(noteId : Int) {
        soundPool.play(noteId, 1f, 1f, 1, 0, 1f)
    }

    private fun playNote(note: String){
        playNote(noteMap[note] ?: 0)
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
    private suspend fun playSong(song: List<Note>){
        for (note in song) {
            when (note.note) {
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
                "LG" -> playNote(lgNote)
                "HA" -> playNote(haNote)
                "HBB" -> playNote(hbbNote)
                "HB" -> playNote(hbNote)
                "HC" -> playNote(hcNote)
                "HCS" -> playNote(hcsNote)
                "HD" -> playNote(hdNote)
                "HDS" -> playNote(hdsNote)
                "HE" -> playNote(heNote)
                "HF" -> playNote(hfNote)
                "HFS" -> playNote(hfsNote)
                "HG" -> playNote(hgNote)
                "HGS" -> playNote(hgsNote)
            }
            delay(note.duration.toLong())
        }
    }
}