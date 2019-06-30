import axios from 'axios';
import { async } from 'q';


export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {

    await axios.post(`/api/backlog/${backlog_id}`, project_task);
    history.push(`/projectBoard/${backlog_id}`);

}