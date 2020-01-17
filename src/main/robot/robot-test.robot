*** Settings ***

Resource            ../CommonResource.robot
Force Tags          MyTag


*** Variables ***

${robotVar} =            FooBarBaz


*** Test cases ***
Foo Test Case
    Foo Java Keyword
    My Foo Bar Keyword
    [tags]              FooTag
    [Documentation]     Created by John Doe
    Do An Action        Argument
    Do Another Action   ${robotVar}

*** Keywords ***

My Foo Bar Keyword
    Foo Java Keyword
    [Documentation]    Does so and so
    [Arguments]        ${arg1}
    Do this
    Do that
    [Return]           Some value
