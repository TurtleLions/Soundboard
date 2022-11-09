package com.mistershorr.soundboard

import android.media.AudioManager
import android.media.SoundPool
import kotlinx.coroutines.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
    var laNote = 0
    var lbbNote = 0
    var lbNote = 0
    var lcNote = 0
    var lcsNote = 0
    var ldNote = 0
    var ldsNote = 0
    var leNote = 0
    var lfNote = 0
    var lfsNote = 0
    var lgNote = 0
    var lgsNote = 0

    var noteMap = HashMap<String, Int>()

    private lateinit var binding: ActivityMainBinding

    lateinit var songStorage: List<Note>
    var songTest = "D 400 HA 0 LF 0 LC 0 C 800 A 400 G 300 E 100 G 0 A 0 LF 0 LC 400 F 0 LG 300 LE 100 "

    //Song Examples
    //Mary Had a Little Lamb: B 400 A 400 LG 400 A 400 B 400 B 400 B 800 A 400 A 400 A 800 B 400 D 400 D 800 B 400 A 400 LG 400 A 400 B 400 B 400 B 400 B 400 A 400 A 400 B 400 A 400 LG 1000
    //Happy Birthday: HC 300 HC 100 F 0 HD 400 HC 400 HF 400 C 0 HE 800 HC 300 HC 100 C 0 HD 400 D 0 HC 400 E 0 HG 400 F 0 HF 800 F 0 HC 400 HA 400 HF 400 BB 0 HE 400 HD 400 HBB 300 HBB 100 C 0 HA 400 HF 400 E 0 G 400 F 0 HF 400
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeSoundPool()
        setListeners()
        importSong()

        GlobalScope.launch {
            var songGet = parser(songTest)
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
        laNote = soundPool.load(this, R.raw.scalelowa, 1)
        lbbNote = soundPool.load(this, R.raw.scalelowbb, 1)
        lbNote = soundPool.load(this, R.raw.scalelowb, 1)
        lcNote =  soundPool.load(this, R.raw.scalelowc, 1)
        lcsNote =  soundPool.load(this, R.raw.scalelowcs, 1)
        ldNote =  soundPool.load(this, R.raw.scalelowd, 1)
        ldsNote =  soundPool.load(this, R.raw.scalelowds, 1)
        leNote =  soundPool.load(this, R.raw.scalelowe, 1)
        lfNote =  soundPool.load(this, R.raw.scalelowf, 1)
        lfsNote =  soundPool.load(this, R.raw.scalelowfs, 1)
        lgNote =  soundPool.load(this, R.raw.scalelowg, 1)
        lgsNote =  soundPool.load(this, R.raw.scalelowgs, 1)

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
        noteMap.put("LA", haNote)
        noteMap.put("LBB", hbbNote)
        noteMap.put("LB", hbNote)
        noteMap.put("LC", hcNote)
        noteMap.put("LCS", hcsNote)
        noteMap.put("LD", hdNote)
        noteMap.put("LDS", hdsNote)
        noteMap.put("LE", heNote)
        noteMap.put("LF", hfNote)
        noteMap.put("LFS", hfsNote)
        noteMap.put("LG", lgNote)
        noteMap.put("LGS", hgsNote)
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
        delay(1000)
        val chordSave = mutableListOf<Note>()
        for (note in song) {
            if(note.duration==0){
                chordSave.add(note)
            }
            else {
                if(chordSave.size>0) {
                    for (cNote in chordSave) {
                        playNote(cNote.note)
                    }
                }
                playNote(note.note)
                delay(note.duration.toLong())
                chordSave.clear()
            }
        }
    }
}