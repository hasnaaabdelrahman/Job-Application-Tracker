import { BrowserRouter, Routes, Route } from "react-router-dom";
import Signup from "./components/Auth/Sign-up";
import Login from "./components/Auth/Login";


function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
