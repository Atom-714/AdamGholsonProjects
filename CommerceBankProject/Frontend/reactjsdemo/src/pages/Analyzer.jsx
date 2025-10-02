
import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';


function Analyzer() {


       
    const[url, setUrl] = useState({
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
              return res.json();
            }else{
              return null;
            }
          })
        .then(res=>{
          console.log(res)
          if(res!==null){
            //navigate("/url");;
          }else{
            alert('fails');
          }
       
        });
   
    }
   


    return (
        <div>
            <Form onSubmit = {submitUrl}>
            
            <Form.Group className="mb-3" controlId="formBasicUrl">
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

export default Analyzer;
