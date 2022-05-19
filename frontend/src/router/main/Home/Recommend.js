import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper.min.css";
import RecommendCard from "./RecommendCard";

const Recommend = ({ title, recommendList, near }) => {
  return (
    <div>
      <div
        style={{
          fontSize: "2.2vh",
          marginBottom: "2vh",
          color: "#8E8E92",
          fontWeight: "400",
          marginLeft: "3vw",
        }}
      >
        {title}
      </div>
      <div>
        <Swiper slidesPerView={1.6} spaceBetween={10} centeredSlides={false} loop={true} className="mySwiper">
          {recommendList.length > 0
            ? recommendList.map((mountain, idx) => (
                <SwiperSlide key={idx}>
                  <RecommendCard mountain={mountain} near={near} />
                </SwiperSlide>
              ))
            : "방문한 산이 없습니다."}
        </Swiper>
      </div>
    </div>
  );
};

export default Recommend;
