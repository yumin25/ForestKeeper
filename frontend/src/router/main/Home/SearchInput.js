import search from "../../../res/img/search.png";
function SearchInput({ keyword, keywordHandler, onSubmit, goSearch }) {
  return (
    <>
      <div id="searchBar" style={{ height: "7vh" }}>
        <div
          style={{ display: "flex", marginTop: "3.5vh", marginBottom: "0.5vh" }}
        >
          <div>
            <input
              className="input"
              value={keyword}
              onKeyPress={onSubmit}
              onChange={keywordHandler}
              style={{
                background: "#EFEFEF",
                width: "77vw",
                height: "5vh",
                borderRadius: 10,
                border: "none",
                paddingLeft: "5vw",
                marginBottom: "1vh",
                marginLeft: "4.5vw",
              }}
            ></input>
          </div>
          <div onClick={goSearch}>
            <img
              style={{
                marginTop: "1.2vh",
                width: "6vw",
                height: "6vw",
                marginLeft: "2vw",
              }}
              src={search}
            />
          </div>
        </div>
      </div>
    </>
  );
}
export default SearchInput;
