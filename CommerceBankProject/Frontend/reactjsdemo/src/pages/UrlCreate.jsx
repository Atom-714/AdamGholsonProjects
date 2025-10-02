
import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';


function UrlCreate() {


       
    const[url, setUrl] = useState({
        name:'',
        url:'',
      });


    const changeValue=(e)=>{
        console.log(e);
        setUrl({
         ...url, [e.target.name]:e.target.value  
        });
        console.log(e.target.name + " name "  );
        console.log(e.target.value + " value " );
      }

    const navigate = useNavigate();

    const submitUrl =(e)=>{
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
            navigate("/url");;
          }else{
            alert('fails');
          }
       
        });
   
    }
   


    return (
        <div>
            <Form onSubmit = {submitUrl}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Url Name</Form.Label>
                <Form.Control type="text" placeholder="Enter Url name" onChange = {changeValue} name = "name"/>
                <Form.Text className="text-muted">
                </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Url</Form.Label>
                <Form.Control type="text" placeholder="Enter url" onChange = {changeValue} name = "url" />
            </Form.Group>  

            <Button variant="primary" type="submit">
                Submit
            </Button>
            </Form>
        </div>
      );
 
}

export default UrlCreate;
