run(){
  java -jar "$HOME/.bm/bm-cli.jar"
  return
}

if [ -f "$HOME/.bm/bm-cli.jar" ]; then
  echo "bm.jar exists"
  run
else
  echo "bm.jar doesnt exist"
  mkdir -p "$HOME/.bm"
  curr=$(pwd)
  cd "$HOME/.bm"
  wget https://raw.githubusercontent.com/Wdboyes13/BetterMake/refs/heads/main/cli/cli/target/bm-cli.jar
  cd "$curr" || exit 1
  run
fi