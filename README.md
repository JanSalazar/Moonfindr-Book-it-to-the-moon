# Moonfindr-Book-it-to-the-moon
NASA app challenge 2016 Liverpool - Book it to the moon
(https://2016.spaceappschallenge.org/challenges/solar-system/book-it-to-the-moon/projects/moonfindr)

Youtube - https://www.youtube.com/watch?v=4Rzn0Dws2Gk


Moonfindr is an educational and fun Moon locating app for children aged 5-10. The app uses an easy to read compass-like interface to get the child to physically turn and point the phone in the direction of the Moon. Getting children to find the Moon in the day time helps develop an understanding of orbits and to visualise celestial mechanics. The app includes pictures, a quiz and lunar data based on the Moon’s current position. It is both a fun app for individuals or an excellent starting point for teachers to use in classes.

The app first calculates the Moon’s co-ordinates on the ecliptic plane based on the time and date taken from the smartphone. This is then translated to a projected point on the horizon. The angle between this projected point and North is called the azimuth. Using the Google maps API the smartphones direction relative to North is also calculated. Comparing the azimuth to the phone’s position relative to North allows the angle between the smartphone and the Moon to be calculated. An onscreen arrow directs the child to turn the phone until they are facing the correct direction. The code can be adapted from this position to then find the pitch angle of the Moon.
