import { Route, Routes } from 'react-router-dom'
import './App.css'
import Header from './components/Header'
import Home from './pages/Home'
import Login from './pages/Login'
import Book from './pages/Book'
import Url from './pages/Url'
import UrlCreate from './pages/UrlCreate'
import { useLocation } from "react-router-dom"
import Analyzer from './pages/Analyzer'
import MainPage from './pages/MainPage'

function App() {
  const location = useLocation();
  return (
    <>
      <div>
        {/*Determines which pages will display Header*/}
        {(location.pathname === "/" || location.pathname === "/mainpage") && <Header />}
        <Routes>
            <Route path="/" element={<Login/>}/>
            {/* {<Route path="/login" element={<Login/>}/>} */}
            {/* <Route path="/book" element={<Book/>}/> */}
            {/* <Route path="/curl" element={<UrlCreate/>}/> */}
            {/* <Route path="/url" element={<Url/>}/> */}
            <Route path="/mainpage" element={<MainPage/>}/>
            {/* <Route path="/analyze" element={<Analyzer/>}/> */}
        </Routes>
      </div>
    </>
  )
}

export default App
