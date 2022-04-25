function Detail() {
  const [Info, setInfo] = useState({
    name: "관악산",
    number: "589",
    distance: "4545",
  });
  function getMountainInfo() {}
  return (
    <>
      <div id="mountainName">{Info.name}</div>
      <div id="banner"></div>
      <div id="tabs">
        <div id="label"></div>
        <div id="tab"></div>
        <div id="contents"></div>
      </div>
    </>
  );
}
export default Detail;
