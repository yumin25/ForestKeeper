import logo from "../../res/img/logo.png";
import "./Login.css";
import axios from "axios";
import { useEffect, useState } from "react";
import { connect } from "react-redux";
import { save } from "../../store/user";
import { useNavigate } from "react-router-dom";
import Send from "../../config/Send";

function Login({ saveUser, userSlice }) {
  const history = useNavigate();
  const [email, setEmail] = useState("");
  const onEmailHandler = (e) => {
    setEmail(e.target.value);
  };
  const [password, setPassword] = useState("");
  const onPasswordHandler = (e) => {
    setPassword(e.target.value);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    const data = {
      email: email,
      password: password,
    };
    Send.post(`/user/login`, JSON.stringify(data))
      .then((res) => {
        if (res.status === 200) {
          window.localStorage.setItem("idToken", JSON.stringify(res.data.accessToken));
          console.log(localStorage.getItem("idToken"));
          axios
            .get(`https://k6a306.p.ssafy.io/api/user/userinfo`, {
              headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + res.data.accessToken,
              },
            })
            .then((response) => {
              saveUser(response.data);
            });
          history("/");
        }
      })
      .catch((e) => {
        console.log(e);
        window.alert("이메일과 비밀번호를 확인해주십시오");
      });
  };
  const handleEnter = (e) => {
    if (e.key === "Enter") {
      onSubmit(e);
    }
  };

  useEffect(() => {
    if (localStorage.getItem("idToken")) {
      history("/accounts/mypage");
    }
  });
  return (
    <div style={{ margin: "10vh" }}>
      <div style={{ display: "flex", alignItems: "center", flexDirection: "column" }}>
        <img style={{ width: "70vw", maxWidth: "400px" }} src={logo} alt="logo" />
        <div style={{ marginBottom: "0.5rem" }}>
          <p style={{ color: "#69696C", marginBottom: "0.5rem" }}>이메일</p>
          <input type="email" className="input" placeholder="forest@keeper.com" onChange={onEmailHandler} />
        </div>
        <div style={{ marginBottom: "2rem" }}>
          <p style={{ color: "#69696C", marginBottom: "0.5rem" }}>비밀번호</p>
          <input type="password" className="input" placeholder="********" onChange={onPasswordHandler} onKeyPress={handleEnter} />
        </div>
        <button
          style={{
            backgroundColor: "#37CD8D",
            border: "none",
            borderRadius: "2rem",
            width: "14rem",
            height: "2.5rem",
            color: "white",
            marginBottom: "0.5rem",
          }}
          onClick={onSubmit}
        >
          로그인
        </button>
        <button
          style={{
            borderColor: "#37CD8D",
            backgroundColor: "#FFFFFF",
            borderStyle: "solid",
            borderWidth: "1px",
            borderRadius: "2rem",
            width: "14rem",
            height: "2.5rem",
          }}
        >
          회원가입
        </button>
      </div>
    </div>
  );
}

function mapStateToProps(state) {
  return { userSlice: state.user };
}

function mapDispatchToProps(dispatch) {
  return { saveUser: (user) => dispatch(save(user)) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
