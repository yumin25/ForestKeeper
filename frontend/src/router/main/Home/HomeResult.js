import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper.min.css";
import RecommendCard from "./RecommendCard";

const HomeResult = ({ mountain }) => {
  let num = 1;
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
          mountain.map((mtn) => (
            <div
              style={{
                display: "flex",
                height: "3.3vh",
                marginBottom: "2vh",
                fontSize: "1.8vh",
              }}
            >
              <div style={{ marginRight: "3vw" }}>
                <b>5</b>
              </div>
              <div style={{ width: "15vw", marginRight: "3vw" }}>
                <b>{mtn.name}</b>
              </div>
              <div
                style={{
                  color: "#ACACAC",
                  width: "38vw",
                  marginRight: "3vw",
                }}
              >
                {mtn.address}
              </div>
              <div>{mtn.count} 회</div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default HomeResult;
