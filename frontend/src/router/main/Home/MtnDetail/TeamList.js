import React, { useEffect, useState, useRef } from "react";
import edit from "../../../../res/img/edit.png";
import Send from "../../../../config/Send";
import { useParams } from "react-router-dom";
function LIST({ result }) {
  return (
    <div
      style={{
        width: "82vw",
        marginTop: "2vh",
        paddingBottom: "2.5vh",
        borderBottom: "1px solid #E5E5E5",
        fontSize: "1.8vh",
        display: "flex",
      }}
    >
      <div id="left" style={{ width: "67vw" }}>
        <div style={{ fontWeight: "bold", marginBottom: "0.5vh" }}>
          {result.ploggingDate}
        </div>
        <div style={{ color: "#69696C" }}>{result.title}</div>
      </div>
      <div id="right" style={{ display: "flex", marginTop: "2vh" }}>
        <div style={{ fontSize: "2.5vh", left: "70vw" }}>
          {result.participant}/{result.total}
        </div>
        <div style={{ fontSize: "2vh", marginTop: "0.5vh", marginLeft: "1vw" }}>
          명
        </div>
      </div>
    </div>
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

function TeamList({ mountainCode }) {
  const [tab, setTab] = useState("REVIEW");
  const [list, setList] = useState([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  let useParam = useParams();
  const getItems = async (page) => {
    getList();
  };

  useEffect(() => {
    getItems(page);
  }, [page]);

  function goDetail(matchingId) {
    window.localStorage.setItem("matchingId", matchingId);
    document.location.href = "/teamDetail";
  }
  function getList() {
    Send.get("/match", {
      params: {
        mountainCode: mountainCode,
        page: page,
      },
    })
      .then(function (response) {
        console.log(response);
        setTotal(response.data.matchingGetListResponseDTOList.length);
        //setSearchList(response.data.searchlist);
        if (page >= 2) {
          setList((list) => [
            ...list,
            ...response.data.matchingGetListResponseDTOList,
          ]);
        } else {
          setList(response.data.matchingGetListResponseDTOList);
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
        <div>
          <div
            onClick={() =>
              (document.location.href = `/teamWrite/${useParam.mountainCode}`)
            }
          >
            <WriteItem />
          </div>
          <div
            id="List"
            style={{ height: "70vh", overflow: "auto", display: "flex" }}
          >
            <div>
              {list &&
                list.map((result) => (
                  <div onClick={() => goDetail(result.id)}>
                    <LIST result={result} />
                  </div>
                ))}

              <div style={{ height: "2vh" }} ref={pageEnd}></div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
export default TeamList;
