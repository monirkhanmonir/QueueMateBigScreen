from gtts import gTTS
import os
import time
from os.path import dirname, join

def genetareTTSFile(queueData, attention_message):
    text = queueData+attention_message
    countdata = 8
    #filename=""

    if countdata > 0 :
        tts = gTTS(text=text, lang='bn')
        try:
         print('1. try to generate audio file..')

         # create gtts file
         tts_file_name = str(int(time.time())) + '.mp3'
         filename = join(dirname(__file__), tts_file_name)
         tts.save(filename)

         print(filename)
         return filename
        except Exception as b:
            print(b)
