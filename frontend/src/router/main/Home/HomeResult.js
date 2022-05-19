import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper.min.css";
import RecommendCard from "./RecommendCard";

const HomeResult = ({ mountain }) => {
  console.log(mountain);
  let num = 0;
  return (
    <div>
      <div
        style={{
          marginLeft: "4vw",
          marginBottom: "2.5vh",

          fontSize: "2.2vh",
          color: "#8E8E92",
          fontWeight: "400",
        }}
      >
        누적 방문 횟수 랭킹
      </div>

      <div style={{ marginLeft: "5vw" }}>
        {mountain &&
          mountain.map((mtn, idx) => (
            <div
              onClick={() =>
                (document.location.href = `/detail/${mtn.mountainCode}`)
              }
              style={{
                display: "flex",
                height: "3.3vh",
                marginBottom: "2vh",
                fontSize: "1.8vh",
              }}
              key={idx}
            >
              <div style={{ marginRight: "3vw" }}>
                <b>{(num += 1)}</b>
              </div>
              <div style={{ width: "15.5vw", marginRight: "2.5vw" }}>
                <b>{mtn.mountainName}</b>
              </div>
              <div
                style={{
                  color: "#ACACAC",
                  width: "43vw",
                  marginRight: "1vw",
                }}
              >
                {mtn.address}
              </div>
              <div
                style={{
                  textAlign: "center",
                  width: "14vw",
                  // background: "red",
                }}
              >
                {mtn.visitorCount} 회
              </div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default HomeResult;
