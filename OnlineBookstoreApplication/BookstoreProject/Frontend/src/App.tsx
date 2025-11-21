import './App.css'
import { Route, Routes } from 'react-router-dom'
import TopNav from './componets/TopNav'
import BookList from './pages/BookList'
import BookCreate from './pages/BookCreate'
import UserCreate from './pages/UserCreate'
import UserLogin from './pages/Login'
import BookListCart from './pages/BookListCart'
import UserPurchases from './pages/UserPurchases'
import BookUpdate from './pages/BookUpdate'

function App() {
 
  return (
    <>
      <TopNav />
      <div className="container mt-4">
        <Routes>
          <Route path="/userlogin" element={<UserLogin />} />
          <Route path="/usercreate" element={<UserCreate />} />
          <Route path="/booklist" element={<BookList />} />
          <Route path="/booklistcart" element={<BookListCart />} />
          <Route path="/bookcreate" element={<BookCreate />} />
          <Route path="/userpurchases" element={<UserPurchases />} />
          <Route path="/bookupdate" element={<BookUpdate />} />
        </Routes>
      </div>
    </>
  )
}

export default App
