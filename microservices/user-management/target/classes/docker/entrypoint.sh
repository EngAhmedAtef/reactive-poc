#!/bin/bash

# Set the entry point of the application to be executed  after the database deployment
ENTRY_POINT_EXECUTION=${ENTRY_POINT}

# Clear all the used environment variables
unset_environment_variables

# Execute the entry point command
eval "$ENTRY_POINT_EXECUTION"
exit 0
