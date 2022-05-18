import React, { useEffect, useState, useRef } from "react";
import edit from "../../../../res/img/edit.png";
import Send from "../../../../config/Send";
import check from "../../../../res/img/check.png";
import checked from "../../../../res/img/checked.png";
import { useParams } from "react-router-dom";
import { connect } from "react-redux";
import Bar from "../Bar";
function LIST({ result, userSlice }) {
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
        <div style={{ display: "flex" }}>
          <div style={{ fontWeight: "bold", marginBottom: "0.5vh" }}>
            {result.ploggingDate}
          </div>
          {userSlice.userNickname == result.nickname ? (
            <div
              style={{
                marginLeft: "2vw",
                width: "8vw",
                background: "#37CD76",
                color: "white",
                borderRadius: 15,
                textAlign: "center",
                height: "3vh",
              }}
            >
              my
            </div>
          ) : (
            <div></div>
          )}
        </div>

        <div style={{ color: "#69696C" }}>{result.title}</div>
      </div>
      <div id="right" style={{ display: "flex", marginTop: "2vh" }}>
        <div style={{ fontSize: "2.5vh", left: "70vw" }}>
          {result.participants.length}/{result.total}
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

function TeamList({ userSlice }) {
  const [tab, setTab] = useState("REVIEW");
  const [list, setList] = useState([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const [Info, setInfo] = useState([]);
  const [mountainId, setMountainId] = useState();
  const [count, setCount] = useState(0);
  let useParam = useParams();
  const getItems = async (page) => {
    getList();
  };
  console.log(useParam.mountainCode);
  useEffect(() => {
    getItems(page);
  }, [page]);

  useEffect(() => {
    getMtnInfo();
  }, []);
  function getMtnInfo() {
    Send.get(`/mountain/${useParam.mountainCode}`)
      .then((res) => {
        if (res.status === 200) {
          console.log(res);
          setInfo(res.data.mountainInfo);
          window.localStorage.setItem("mountainId", res.data.mountainInfo.id);
          setMountainId(res.data.mountainInfo.id);
          getTotal();
        }
      })
      .catch((e) => {
        console.log(e);
      });
  }

  function getTotal() {
    Send.get(`/plogging`, {
      params: {
        mountainCode: useParam.mountainCode,
      },
    })
      .then(function (response) {
        console.log(response.data);
        setCount(response.data.count);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function goDetail(matchingId) {
    window.localStorage.setItem("matchingId", matchingId);
    document.location.href = "/teamDetail";
  }
  function getList() {
    Send.get("/match", {
      params: {
        mountainCode: useParam.mountainCode,
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
      <div
        id="header"
        style={{ marginLeft: "6vw", marginRight: "6vw", height: "5vh" }}
      ></div>

      <div
        style={{
          marginLeft: "6vw",
          marginRight: "6vw",
          height: "4.5vh",
          display: "flex",

          marginBottom: "1vh",
        }}
      >
        <div
          id="mountainName"
          style={{
            marginLeft: "3vw",
            fontSize: "3.5vh",
            fontWeight: "500",
          }}
        >
          {Info.name}
        </div>

        <div>
          {count == 0 ? (
            <img
              style={{
                marginLeft: "2vw",
                marginTop: "1.2vh",
                width: "3vh",
                height: "3vh",
              }}
              src={check}
            />
          ) : (
            <div style={{ color: "#69696C", display: "flex" }}>
              <img
                style={{
                  marginLeft: "2vw",
                  marginTop: "1.2vh",
                  width: "3vh",
                  height: "3vh",
                  marginRight: "1.5vw",
                }}
                src={checked}
              />
              <div style={{ marginTop: "1.5vh", fontSize: "1.8vh" }}>
                {count}회 방문
              </div>
            </div>
          )}
        </div>
      </div>

      <div
        id="address"
        style={{
          marginLeft: "9vw",
          marginRight: "6vw",
          marginBottom: "3.8vh",
          fontSize: "1.8vh",
          color: "#69696C",
        }}
      >
        {Info.address}
      </div>
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
            style={{ height: "72.5vh", overflow: "auto", display: "flex" }}
          >
            <div>
              {list &&
                list.map((result) => (
                  <div onClick={() => goDetail(result.id)}>
                    <LIST result={result} userSlice={userSlice} />
                  </div>
                ))}

              <div style={{ height: "2vh" }} ref={pageEnd}></div>
            </div>
          </div>
        </div>
      </div>
      <Bar></Bar>
    </>
  );
}
function mapStateToProps(state) {
  return { userSlice: state.user };
}

export default connect(mapStateToProps)(TeamList);
