# ucmo


from dotenv import load_dotenv
load_dotenv()
import asyncio  


from agents import Agent, Runner, function_tool, SQLiteSession


session = SQLiteSession("user1", "memory.db")




@function_tool
def get_course_description(course_code: str) -> str:


    code = course_code.replace(" ", "").lower()


    catalog = {
        "se2920": (
            "SE 2920 — Development Tools \n"
            "An introduction to software development tools. "
        ),
        "se3910": (
            "SE 3910 — Software Engineering \n"
            "An introduction to software development process (Agile, Lean, Scrum and Kanban), with emphasis on software design, team management, and application development. "


        ),
        "se4950": (
            "SE 4950 — Secure Software Engineering\n"
            "In depth study of secure development lifecycle"
            "Students will learn how to practice risk analysis, static/dynamic analysis, penetration testing, and secure code review in a dialectic process. \n"
        ),
    }


    # Normalize a few common inputs like "SE 2920", "se-2920", etc.
    normalized = (
        code.replace("-", "")
            .replace("_", "")
    )


    if normalized in catalog:
        return catalog[normalized]


    return (
        "Unknown course code. Try one of: SE 2920, SE 3910, SE 4950.\n"
    )


# (Optional) A tiny helper to list supported courses
@function_tool
def list_supported_courses() -> str:
    return "Supported courses: SE 2920, SE 3910, SE 4950"


# --- Agent: UCM Undergraduate Coordinator -----------------------------------
agent = Agent(
    name="UCM Undergraduate Coordinator Agent",
    instructions=(
        "You are the UCM Computer Science Undergraduate Coordinator. "
    ),
    tools=[get_course_description, list_supported_courses],
)






async def repl():
    print("Type 'exit' to quit.")
    while True:
        q = input("Ask: ").strip()
        if q.lower() in {"exit", "quit"}:
            break
        result = await Runner.run(agent, q, session=session)
        print(result.final_output)


def main():   
    asyncio.run(repl())


if __name__ == "__main__":
    main()