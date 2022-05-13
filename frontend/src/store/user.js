import { createSlice } from "@reduxjs/toolkit";

const initialUserState = {
  userEmail: "",
  userNickname: "",
  userName: "",
};

const userSlice = createSlice({
  name: "user",
  initialState: initialUserState,
  reducers: {
    save: (state, action) => {
      state.userEmail = action.payload.email;
      state.userNickname = action.payload.nickname;
      state.userName = action.payload.name;
    },
    update: (state, action) => {
      state.userNickname = action.payload.nickname;
    },
  },
});

export const { save, update } = userSlice.actions;
export default userSlice.reducer;
