import React, { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import temp from "../../../../res/img/temp.png";
import axios from "axios";
import edit from "../../../../res/img/edit.png";
function ReviewItem({ nickname, content }) {
  return (
    <div
      style={{
        marginTop: "1.5vh",
        paddingBottom: "1.5vh",
        // background: "red",
        borderBottom: "1px solid #E5E5E5",
        fontSize: "1.5vh",
      }}
    >
      <div style={{ fontWeight: "bold", marginBottom: "0.5vh" }}>
        {nickname}
      </div>
      <div style={{ color: "#69696C" }}>{content}</div>
    </div>
  );
}
function QnaItem({ nickname, content, title }) {
  return (
    <>
      <div
        style={{
          // height: "9vh",
          borderBottom: "1px solid #CDCDCD",
          fontSize: "1.5vh",
        }}
      >
        <div
          style={{
            marginTop: "1.5vh",
            fontWeight: "bold",
            marginBottom: "0.5vh",
          }}
        >
          {nickname}
        </div>
        <div style={{ marginBottom: "0.5vh" }}>{title}</div>
        <div style={{ color: "#8E8E92", marginBottom: "1.5vh" }}>{content}</div>
      </div>
    </>
  );
}

function WriteItem() {
  return (
    <>
      <div style={{ display: "flex" }}>
        <div
          style={{
            marginTop: "0.5vh",
            textAlign: "right",
            fontSize: "1.5vh",
            color: "#8E8E92",
            marginLeft: "65vw",
          }}
        >
          글쓰기
        </div>
        <div>
          <img
            style={{
              marginLeft: "2vw",
              width: "2vh",
              height: "2vh",
            }}
            src={edit}
          />
        </div>
      </div>
    </>
  );
}

function About({ url }) {
  const [tab, setTab] = useState("REVIEW");
  const [list, setList] = useState([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  let useParam = useParams();
  const ClickedStyle = {
    borderRadius: 3,
    width: "20vw",
    height: "3vh",
    background: "#A2CA9D",
    color: "white",
    border: "none",
    marginRight: "1vw",
  };
  const UnClickedStyle = {
    borderRadius: 3,
    width: "20vw",
    height: "3vh",
    background: "#DAEDD7",
    color: "white",
    border: "none",
    marginRight: "1vw",
  };

  const getItems = async (page) => {
    getList();
  };

  useEffect(() => {
    getItems(page);
  }, [page]);

  function goDetail() {}

  function getList() {
    axios
      .get(url + `/community`, {
        params: {
          communityCode: tab,
          page: page,
        },
      })
      .then(function (response) {
        console.log(response.data);
        //setSearchList(response.data.searchlist);
        setList((list) => [
          ...list,
          ...response.data.communityGetListResponseDTOList,
        ]);
        setLoading(true);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  const loadMore = () => {
    setPage((prevPageNumber) => prevPageNumber + 1);
  };

  const pageEnd = useRef();

  useEffect(() => {
    if (loading) {
      const observer = new IntersectionObserver(
        (entries) => {
          if (entries[0].isIntersecting) {
            loadMore();
          }
        },
        { threshold: 1 }
      );
      observer.observe(pageEnd.current);
    }
  }, [loading]);

  return (
    <>
      <div style={{ marginLeft: "9vw", marginRight: "9vw" }}>
        <div id="tab" style={{ marginBottom: "1vh" }}>
          {tab == "REVIEW" ? (
            <>
              <button style={ClickedStyle}>등산후기</button>
              <button onClick={() => setTab("QNA")} style={UnClickedStyle}>
                Q&A
              </button>
            </>
          ) : (
            <>
              <button onClick={() => setTab("REVIEW")} style={UnClickedStyle}>
                등산후기
              </button>
              <button style={ClickedStyle}>Q&A</button>
            </>
          )}
        </div>

        <div id="List" style={{ height: "62vh" }}>
          {tab == "review" ? (
            <>
              <div
                onClick={() =>
                  (document.location.href = `/articleWrite/${useParam.mountainCode}`)
                }
              >
                <WriteItem />
              </div>

              {list &&
                list.map((result) => (
                  <div onClick={() => goDetail(result)}>
                    <ReviewItem result={result} />
                  </div>
                ))}

              <div style={{ height: "2vh" }} ref={pageEnd}></div>
            </>
          ) : (
            <>
              <div
                onClick={() =>
                  (document.location.href = `/articleWrite/${useParam.mountainCode}`)
                }
              >
                <WriteItem />
              </div>

              {list &&
                list.map((result) => (
                  <div onClick={() => goDetail(result)}>
                    <QnaItem result={result} />
                  </div>
                ))}

              <div style={{ height: "2vh" }} ref={pageEnd}></div>
            </>
          )}
        </div>
      </div>
    </>
  );
}
export default About;
