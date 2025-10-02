import { useEffect, useState } from "react";
import { Button, Card, Form, Table } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';

function MainPage() {

  
    const[url, setUrl] = useState({
        name:'',
        url:'',
      });

    // const [analysisResults, setAnalysisResults] = useState("");

    const [responseHeaders, setResHeaders] = useState([]);
    const [certificateHeaders, setCertHeaders] = useState([]);
    const [cipherSuite, setCipherSuite] = useState("");

    const [urls, setUrls] = useState([]);
    const [loggedIn, setLoggedIn] = useState(0);
    
    useEffect(()=>{
      refreshTable();
    },[])   

    const refreshTable=()=>{
      fetch(" http://localhost:8081/urls", {method:"GET"})
        .then(res =>res.json())
        .then(res=>{
          fetch(" http://localhost:8081/findLogin", {method:"GET"})
          .then(uid => uid.json())
          .then(uid => {
            //console.log(10, uid);
            //console.log(res);
            setUrls(res.filter(function(url){return url.customer.customer_id === uid}));
          });
        });
    }

    const changeValue=(e)=>{
        console.log(e);
        setUrl({
         ...url, [e.target.name]:e.target.value  
        });
        console.log(e.target.name + " name "  );
        console.log(e.target.value + " value " );
      }

    // const navigate = useNavigate();

    const addUrl =(e)=>{
        console.log("AddUrl");
        e.preventDefault();
        fetch(" http://localhost:8081/url", {
          method:"POST",
          headers:{
            "Content-Type" : "application/json"
          },
          body: JSON.stringify(url)
        })
        .then(res=>{
            console.log(1,res);
            if(res.status === 201){
              return res.json();
            }else{
              return null;
            }
          })
        .then(res=>{
          console.log(res)
          if(res!==null){
            refreshTable();
            // navigate("/url");;
          }else{
            alert('Invalid Input');
          }
       
        });
   
    }

    const deleteUrl = (id)=>{
      console.log("Delete " + id);
      fetch(" http://localhost:8081/deleteUrl/" + id, {method:"DELETE"})
        .then(res =>{
          refreshTable();
      });
    }

    const analyzeUrl =(e)=>{
        console.log("analyzeUrl");
        e.preventDefault();
        doAnalysis();
    }

    const doAnalysis=()=>{
      fetch(" http://localhost:8081/analyze", {
        method:"POST",
        headers:{
          "Content-Type" : "application/json"
        },
        body: JSON.stringify(url)
      })
      .then(res=>{
          console.log(1,res);
          if(res.status === 200){
              
          //   let obj = res.json()
          //   console.log(res.json().headers)
          //   console.log(res.headers)
          //   setResHeaders(res.headers);

            //setAnalysisResults(res.text());
            return res.json();
          }else{
            return null;
          }
        })
      .then(res=>{
        console.log(2, res)
        if(res!==null){
          //console.log(3, res.certificates)
          setResHeaders(res.headers)
          setCertHeaders(res.certificates)
          setCipherSuite(res.cipherSuite)
          
          //navigate("/url");;
        }else{
          alert('Enter a Valid URL.');
        }
     
      });
    }

    const quickAnalyze=(idx)=>{
      let n = urls[idx].name
      let u = urls[idx].url

      setUrl({
        ...url, name:n, url:u
       });
    }

    const urlEditName=(e)=>{
      console.log(e.target.name + " " + e.target.value);
      
      let n = null;
      let u = null;

      const newUrls = urls.map(url =>{
        if (url.id == e.target.name){
          n = e.target.value;
          u = url.url;
          return {...url, name: e.target.value};
        }
        else{
          return url;
        }
      });

      setUrls(newUrls);

      fetch(" http://localhost:8081/updateUrl", {
        method:"POST",
        headers:{
          "Content-Type" : "application/json"
        },
        body: JSON.stringify({
          id: e.target.name,
          name: n,
          url: u
        })
      })

    }

    const urlEditUrl=(e)=>{
      console.log(e.target.name + " " + e.target.value);
      
      let n = null;
      let u = null;

      const newUrls = urls.map(url =>{
        if (url.id == e.target.name){
          u = e.target.value;
          n = url.name;
          return {...url, url: e.target.value};
        }
        else{
          return url;
        }
      });

      setUrls(newUrls);

      fetch(" http://localhost:8081/updateUrl", {
        method:"POST",
        headers:{
          "Content-Type" : "application/json"
        },
        body: JSON.stringify({
          id: e.target.name,
          name: n,
          url: u
        })
      })

    }

    return (
        <div>
            {/* Url inputs */}
            <Form onSubmit = {addUrl}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Url Name</Form.Label>
                <Form.Control type="text" placeholder="Enter Url name" onChange = {changeValue} name = "name" value={url.name}/>
                <Form.Text className="text-muted">
                </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Url</Form.Label>
                <Form.Control type="text" placeholder="Enter url" onChange = {changeValue} name = "url" value={url.url}/>
            </Form.Group>  

            {/* Add Button */}
            <Button variant="primary" type="submit">
                Add
            </Button>
            </Form>

            {/* Analyze Button */}
            <Form onSubmit={analyzeUrl}>
            <Button variant="primary" type="submit">
                Analyze
            </Button>
            </Form>

            {/* Analysis Output Box */}
            <div  style={{ overflow: "scroll", height:"450px"}}>

                <h4>Cipher Suite: </h4>
                <p>{cipherSuite}</p>

                <h4>Response Headers:</h4>
                <p>
                {Object.entries(responseHeaders).map(([key, value]) =>
                    <span>{key} : {value}<br></br></span>
                )}
                </p>

                <h4>Certificates: </h4>
                {
                    certificateHeaders.map(cert =>
                        <span>
                        {Object.entries(cert).map(([key, value]) =>
                            <span>{key} : {value}<br></br></span>)}
                        <br></br><br></br>
                        </span>
                )}
            </div>

            
            {/* Url Database Table */}
            <Table striped bordered hover>
            <thead>
              <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Url</th>
                <th>Analyze</th>
                {/* <th>Edit</th> */}
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
            {urls.map((url, idx) =>
              <tr key = {url.id}>
                {/* <td><Form.Text className="text-muted">{url.id}</Form.Text></td> */}
                <td>{url.id}</td>
                <td>
                  <Form.Control type="text" value={url.name} name = {url.id} onChange={urlEditName}/>
                </td>
                <td>
                <Form.Control type="text" value={url.url} name = {url.id} onChange={urlEditUrl}/>
                </td>
                <td><Button variant="primary" type="submit" onClick={()=>quickAnalyze(idx)}>Load</Button></td>
                {/* <td><Button variant="primary" type="submit">Edit</Button></td> */}
                <td><Button variant="primary" type="submit" onClick={()=>deleteUrl(url.id)}>Delete</Button></td>
              </tr>
            )}
            </tbody>
            </Table>

        </div>
    );


}

export default MainPage;