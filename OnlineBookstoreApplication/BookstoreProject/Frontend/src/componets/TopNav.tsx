import { useEffect, useState } from 'react';
import axios from 'axios';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

function TopNav() {
  const [username, setUsername] = useState<string | null>(null);

  useEffect(() => {
    axios.get('/api/user/current')
      .then(res => setUsername(res.data.username))
      .catch(() => setUsername(null));
  }, []);

  return (
    <Navbar expand="lg" className="bg-body-tertiary" fixed="top">
      <Container>
        <Navbar.Brand href="#home">Online Bookstore</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <NavDropdown title="Users" id="basic-nav-dropdown">
              <NavDropdown.Item href="/userlogin">Login</NavDropdown.Item>
              <NavDropdown.Item href="/usercreate">Create User</NavDropdown.Item>
              <NavDropdown.Item href="/booklistcart">Cart</NavDropdown.Item>
              <NavDropdown.Item href="/userpurchases">Purchases</NavDropdown.Item>
            </NavDropdown>
            <Nav.Link href="/booklist">BookList</Nav.Link>
            <Nav.Link href="/bookcreate">BookCreate</Nav.Link>
            <Nav.Link href="/bookupdate">BookUpdate</Nav.Link>
          </Nav>
          {username ? (
            <Nav className="ms-auto">
              <Nav.Link disabled style={{ fontWeight: 'bold', color: 'black' }}>
                {username}
              </Nav.Link>
            </Nav>
          ) : (
            <Nav className="ms-auto">
              <Nav.Link href="/userlogin" style={{ color: 'gray' }}>
                Not logged in
              </Nav.Link>
            </Nav>
          )}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default TopNav;