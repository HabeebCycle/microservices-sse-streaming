set -B                  # enable brace expansion
for i in {1..1000}; do
  curl -s -k 'GET' -H 'header info' -b 'stuff' 'http://127.0.0.1:7000/message/stream/interval='$i
done