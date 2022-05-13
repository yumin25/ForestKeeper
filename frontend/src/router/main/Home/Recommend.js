import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper.min.css";
import RecommendCard from "./RecommendCard";

const Recommend = ({ title, recommendList, near }) => {
  return (
    <div>
      <div style={{ margin: "5%" }}>{title}</div>
      <Swiper
        slidesPerView={1.6}
        spaceBetween={10}
        centeredSlides={true}
        loop={true}
        className="mySwiper"
      >
        {recommendList.length > 0
          ? recommendList.map((mountain) => (
              <SwiperSlide>
                <RecommendCard mountain={mountain} near={near} />
              </SwiperSlide>
            ))
          : "방문한 산이 없습니다."}
      </Swiper>
    </div>
  );
};

export default Recommend;
