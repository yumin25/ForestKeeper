import React, { useState, useRef } from "react";
import axios from "axios";
import Avatar from "@mui/material/Avatar";
import add from "../../res/img/add.png";
import "./Signup.css";
import { checkId, checkEmail, checkName, checkNickname } from "./ValidCheck";

function Signup() {
  const titleStyle = {
    color: "#69696C",
    marginBottom: "8%",
  };
  const inputStyle = {
    width: "100%",
    height: "100%   ",
    borderLeft: "0",
    borderRight: "0",
    borderTop: "0",
    borderBottom: "1",
  };
  const [image, setImage] = useState(
    "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
  );
  const fileInput = useRef(null);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [name, setName] = useState("");
  const [nickname, setNickname] = useState("");
  const url = "";

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };

  const onPasswordConfirmHandler = (event) => {
    setPasswordConfirm(event.currentTarget.value);
  };

  const onNameHandler = (event) => {
    setName(event.currentTarget.value);
  };
  const onNicknameHandler = (event) => {
    setNickname(event.currentTarget.value);
  };

  function CheckId() {
    checkId(email, url);
  }
  function CheckEmail() {
    checkEmail(email);
  }
  function CheckName() {
    checkName(name);
  }
  function CheckNickname() {
    checkNickname(nickname, url);
  }

  const onImageHandler = (e) => {
    if (e.target.files[0]) {
      setImage(e.target.files[0]);
    } else {
      //업로드 취소할 시
      setImage(
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
      );
      return;
    }
    //화면에 프로필 사진 표시
    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2) {
        setImage(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  const onCreate = () => {
    if (
      email === "" ||
      password === "" ||
      passwordConfirm === "" ||
      name === "" ||
      nickname === ""
    ) {
      alert("입력하지 않은 정보가 있습니다. 확인해주세요.");
    } else if (!/^[a-zA-Z0-9!@#$%\^&*()]{8,}$/.test(password)) {
      alert(
        "비밀번호는 영문자, 숫자, 특수문자 !@#$%^&*() 포함 8자 이상이어야 합니다."
      );
      return;
    } else if (password != passwordConfirm) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    } else {
      console.log(email, password, name, nickname);
      let formData = new FormData();
      axios
        .post(url + `/user`, {
          email: email,
          password: password,
          nickname: nickname,
          name: name,
        })
        .then(function (response) {
          console.log(response);
          if (response.status === 200) {
            setEmail("");
            setPassword("");
            setPasswordConfirm("");
            setName("");
            setNickname("");
            alert("회원가입이 완료되었습니다!");
            document.location.href = "/accounts/login";
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  };

  return (
    <>
      <div style={{ width: "100vw", height: "100vh" }}>
        {/* <div id="topBlank" style={{ height: "10vh", background: "blue" }} /> */}
        <div id="image" style={{ height: "16vh", marginTop: "10vh" }}>
          <Avatar
            src={image}
            sx={{ width: "30vw", height: "15vh" }}
            style={{
              marginLeft: "32.5%",
              marginRight: "32.5%",
              position: "relative",
            }}
            onClick={() => {
              fileInput.current.click();
            }}
          />

          <img
            src={add}
            style={{
              position: "absolute",
              marginLeft: "34%",
              top: "25%",
              width: "8%",
              height: "4%",
            }}
          ></img>

          <input
            ref={fileInput}
            type="file"
            style={{ display: "none" }}
            className="imgInput"
            id="profile_img"
            accept="image/*"
            name="file"
            onChange={onImageHandler}
          />
        </div>

        <div
          id="form"
          style={{
            // background: "red",
            marginTop: "10%",
            marginLeft: "12%",
            marginRight: "12%",
            width: "76%",
            height: "50vh",
          }}
        >
          <div id="email">
            <div id="title" style={titleStyle}>
              이메일
            </div>
            <div id="input">
              <input
                type="email"
                className="input"
                placeholder="forest@keeper.com"
                onChange={onEmailHandler}
              />
            </div>
          </div>

          <div id="name">
            <div id="title" style={titleStyle}>
              이름
            </div>
            <div id="input">
              <input
                type="text"
                className="input"
                placeholder="2자 이상 10자 이하(한글만 허용됩니다)"
                onChange={onNameHandler}
              />
            </div>
          </div>

          <div id="nickname">
            <div id="title" style={titleStyle}>
              닉네임
            </div>
            <div id="input">
              <input
                type="text"
                className="input"
                placeholder="2자 이상 10자 이하"
                onChange={onNicknameHandler}
              />
            </div>
          </div>

          <div id="password">
            <div id="title" style={titleStyle}>
              비밀번호
            </div>
            <div id="input">
              <input
                type="password"
                className="input"
                placeholder="영문자, 숫자, 특수문자 !@#$%\^&*() 포함 8자 이상"
                onChange={onPasswordHandler}
              />
            </div>
          </div>

          <div id="name"></div>
          <div id="nickname"></div>
          <div id="password"></div>
          <div id="passwordConfirm"></div>
        </div>
        <div id="button" style={{ background: "yellow", height: "25vh" }}>
          dd
        </div>
      </div>
    </>
  );
}
export default Signup;
