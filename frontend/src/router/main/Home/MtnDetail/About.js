import React, { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import temp from "../../../../res/img/temp.png";
import edit from "../../../../res/img/edit.png";
import Send from "../../../../config/Send";
function ReviewItem({ result }) {
  return (
    <div
      style={{
        marginTop: "1.5vh",
        paddingBottom: "1.5vh",
        borderBottom: "1px solid #E5E5E5",
        fontSize: "1.5vh",
      }}
    >
      <div style={{ fontWeight: "bold", marginBottom: "0.5vh" }}>
        {result.nickname}
      </div>
      <div style={{ color: "#69696C" }}>{result.content}</div>
    </div>
  );
}
function QnaItem({ result }) {
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
          {result.nickname}
        </div>
        <div style={{ marginBottom: "0.5vh" }}>{result.title}</div>
        <div style={{ color: "#8E8E92", marginBottom: "1.5vh" }}>
          {result.content}
        </div>
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
  const [total, setTotal] = useState(0);
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

  // useEffect(() => {
  //   if (page != 1 && total != 0) {
  //     if (total >= page * 8) {
  //       getItems(page);
  //     }
  //   } else {
  //     getItems(page);
  //   }
  // }, [page]);

  useEffect(() => {
    setPage(1);
    getItems(page);
  }, [tab]);

  function goDetail(communityId) {
    window.localStorage.setItem("communityId", communityId);
    document.location.href = "/articleDetail";
  }

  function getList() {
    Send.get(url + `/community`, {
      params: {
        communityCode: tab,
        page: page,
      },
    })
      .then(function (response) {
        console.log(response);
        setTotal(response.data.communityGetListResponseDTOList.length);
        //setSearchList(response.data.searchlist);
        if (page >= 2) {
          setList((list) => [
            ...list,
            ...response.data.communityGetListResponseDTOList,
          ]);
        } else {
          setList(response.data.communityGetListResponseDTOList);
        }
        console.log(list);
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

        <div>
          <div
            onClick={() =>
              (document.location.href = `/articleWrite/${useParam.mountainCode}`)
            }
          >
            <WriteItem />
          </div>
          <div id="List" style={{ height: "60vh", overflow: "auto" }}>
            {tab == "review" ? (
              <>
                {list &&
                  list.map((result) => (
                    <div onClick={() => goDetail(result.communityId)}>
                      <ReviewItem result={result} />
                    </div>
                  ))}

                <div style={{ height: "2vh" }} ref={pageEnd}></div>
              </>
            ) : (
              <>
                {list &&
                  list.map((result) => (
                    <div onClick={() => goDetail(result.communityId)}>
                      <QnaItem result={result} />
                    </div>
                  ))}

                <div style={{ height: "2vh" }} ref={pageEnd}></div>
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
export default About;
