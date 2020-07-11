/*
   Copyright 2020 Jörg Pfründer

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
File songsDir = new File("Songs")


File[] songFiles = songsDir.listFiles()


for (File songFile : songFiles) {

    try {
        def song = new XmlSlurper().parseText(songFile.getText("UTF-8"))

        String title = song.title.text()

        String lyrics = song.lyrics.text()

        String author = song.author.text()

        String copyright = song.copyright.text()

        String ccli = song.ccli.text();



        File output = new File(songFile.name + ".html")
        if (output.exists()) {
            output.delete();
        }
        if (!output.exists()) {
            output.createNewFile()
        }
        output.write("<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\"/>\n" +
                "<title>$title</title>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"openSong.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1 class=\"songtitle\">$title</h1>\n" +
                "<h2 class=\"author\">$author</h2>\n" +
                "<p class=\"lyrics\">$lyrics</p>\n" +
                (copyright.isEmpty()?"":"<p class=\"copyright\">$copyright</p>\n") +
                (ccli.isEmpty()?"":"<p class=\"ccli\">$ccli</p>\n") +
                "</body>\n" +
                "</html>\n", "UTF-8"
        )

    }
    catch (Exception e) {
        System.err.println("could not process $songFile.name")
        e.printStackTrace()
    }

}

StringBuilder inhalt = new StringBuilder()


def songFilesSorted = songFiles.sort { it -> it.name }

for (File songFile : songFilesSorted) {
    inhalt.append("<li><a href=\"${songFile.name}.html\">${songFile.name}</a></li>\n")
}


File output = new File("index.html")
output.createNewFile()
output.write("<html>\n" +
        "<head>\n" +
        "<title>Content</title>\n" +
        "<link rel=\"stylesheet\" type=\"text/css\" href=\"openSong.css\">\n" +
        "</head>\n" +
        "<body>\n"+
        "<ul>\n"+
        inhalt+
        "</ul>\n"+
        "</body>\n"+
        "</html>\n","UTF-8"
)

println songsDir
