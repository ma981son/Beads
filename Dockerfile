FROM hseeberger/scala-sbt
WORKDIR /beads
ADD . /beads
CMD sbt test