#!/bin/bash

curl -s -o foo1 http://127.0.0.1:7000/message/stream/interval && echo "done1" &
curl -s -o bar2 http://127.0.0.1:7000/message/stream/interval && echo "done2" &
curl -s -o baz3 http://127.0.0.1:7000/message/stream/interval && echo "done3" &
curl -s -o foo4 http://127.0.0.1:7000/message/stream/interval && echo "done4" &
curl -s -o bar5 http://127.0.0.1:7000/message/stream/interval && echo "done5" &
curl -s -o baz6 http://127.0.0.1:7000/message/stream/interval && echo "done6" &
curl -s -o foo7 http://127.0.0.1:7000/message/stream/interval && echo "done7" &
curl -s -o bar8 http://127.0.0.1:7000/message/stream/interval && echo "done8" &
curl -s -o baz9 http://127.0.0.1:7000/message/stream/interval && echo "done9" &
curl -s -o foo10 http://127.0.0.1:7000/message/stream/interval && echo "done10" &
curl -s -o bar11 http://127.0.0.1:7000/message/stream/interval && echo "done11" &
curl -s -o baz12 http://127.0.0.1:7000/message/stream/interval && echo "done12" &
curl -s -o baz13 http://127.0.0.1:7000/message/stream/interval && echo "done13" &
curl -s -o foo14 http://127.0.0.1:7000/message/stream/interval && echo "done14" &
curl -s -o bar15 http://127.0.0.1:7000/message/stream/interval && echo "done15" &
curl -s -o baz16 http://127.0.0.1:7000/message/stream/interval && echo "done16" &
curl -s -o foo17 http://127.0.0.1:7000/message/stream/interval && echo "done17" &
curl -s -o bar18 http://127.0.0.1:7000/message/stream/interval && echo "done18" &
curl -s -o baz19 http://127.0.0.1:7000/message/stream/interval && echo "done19" &
curl -s -o baz20 http://127.0.0.1:7000/message/stream/interval && echo "done20" &

wait