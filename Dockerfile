FROM hseeberger/scala-sbt
WORKDIR /Beads
ADD . /Beads
CMD sbt test